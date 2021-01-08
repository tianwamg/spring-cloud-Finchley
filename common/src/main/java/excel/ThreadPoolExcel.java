package excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * 使用线程池导出excel
 */
public class ThreadPoolExcel {

    public static void main(String args[]) throws InterruptedException {

        //处理器核心数
        int process = Runtime.getRuntime().availableProcessors();
        //HSSFWorkbook一个页只能写不超过65535条数据
        HSSFWorkbook workbook = new HSSFWorkbook();

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFSheet sheet = workbook.createSheet();

        HSSFRow hssfRow = sheet.createRow(0);
        HSSFCell cell = hssfRow.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("第1个sheet页，第一行，第一个单元格");

        cell = hssfRow.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("第2个sheet页，第一行，第一个单元格");

        cell = hssfRow.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("第3个sheet页，第一行，第一个单元格");

        //手动创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(process, process , 1000, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10000), r -> {
                    Thread t = new Thread(r);
                    System.out.println("当前执行的线程是: "+t.getName());
                    return t;
                });
        //设置🤹计数器
        CountDownLatch countDownLatch = new CountDownLatch(process);
        for(int i=1;i<=process;i++){
            int start = (i-1)*100+1;
            int end = i * 100;
            executorService.execute(()->createRows(sheet,start,end,countDownLatch));
        }
        //线程等待
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        FileOutputStream outputStream = null;
        try{
           outputStream = new FileOutputStream("");
           workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createRows(HSSFSheet sheet,int startRow,int endRow, CountDownLatch countDownLatch){
        HSSFRow rows;
        HSSFCell cells;
        int i = startRow;
        try{
            while (i<=endRow){
                rows = getRows(sheet,i);
                cells = rows.createCell(0);
                cells.setCellValue("第" + (i+1) + "行,第一个单元格");

                cells = rows.createCell(1);
                cells.setCellValue("第" + (i+1) + "行,第二个单元格");

                cells = rows.createCell(2);
                cells.setCellValue("第" + (i+1) + "行,第三个单元格");
            }
        }finally {
            countDownLatch.countDown();
        }
    }

    //创建行,需要加锁
    public static HSSFRow getRows(HSSFSheet sheet,int row){
        synchronized (Object.class){
            return sheet.createRow(row);
        }
    }
}
