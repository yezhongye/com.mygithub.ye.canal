package com.ye.canal.client.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.ye.canal.client.util.RedisUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by zjx on 2017/12/13 0013.
 */
public class CanalClientSample {
    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry( List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
                //=====================
                if (eventType == CanalEntry.EventType.DELETE) {
                    System.out.println("redis del-------> before");
                    redisDelete(rowData.getAfterColumnsList());
                    System.out.println("redis del-------> after");
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    System.out.println("redis insert-------> before");
                    redisInsert(rowData.getAfterColumnsList());
                    System.out.println("redis insert-------> after");
                } else {
                    System.out.println("redis update-------> before");
                    redisUpdate(rowData.getAfterColumnsList());
                    System.out.println("redis update-------> after");
                }
            }
        }
    }

    private static void printColumn( List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
    private static void redisInsert( List<CanalEntry.Column> columns){
        JSONObject json=new JSONObject();
        for (CanalEntry.Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtils.stringSet("test:"+ columns.get(0).getValue(),json.toJSONString());
        }
        System.out.println("insert key is "+"test:" + columns.get(0).getValue());
    }

    private static  void redisUpdate( List<CanalEntry.Column> columns){
        JSONObject json=new JSONObject();
        for (CanalEntry.Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtils.stringSet("test:"+ columns.get(0).getValue(),json.toJSONString());
        }
        System.out.println("update key is "+"test:" + columns.get(0).getValue());
    }

    private static  void redisDelete( List<CanalEntry.Column> columns){
        JSONObject json=new JSONObject();
        for (CanalEntry.Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if(columns.size()>0){
            RedisUtils.delKey("test:" + columns.get(0).getValue());
            System.out.println("del key is "+"test:" + columns.get(0).getValue());
        }
    }
}
