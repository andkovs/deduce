package dao;

import dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class FileDAO {
    public XSSFWorkbook getExcelResultsByGroupId(int id) {
        GroupDTO group = new GroupDAO().getGroupByIdFromDb(id);
        ArrayList<ResultForExcelListDTO> results = new ArrayList<ResultForExcelListDTO>();
        ArrayList<OrderForListDTO> orders = new OrderDAO().getOrdersByGroupIdFromDb(id);
        for (OrderForListDTO o :
                orders) {
            UserDTO user = new UserDAO().getUserByIdFromDb(o.getUserId());
            ArrayList<ResultDTO> orderResults = new ResultDAO().getAllResultsByOrderIdFromDb(o.getId());
            for (ResultDTO r :
                    orderResults) {
                results.add(new ResultForExcelListDTO(r.getId(), user.getLastName(),
                        user.getFirstName(), user.getMiddleName(), user.getCompanyTitle(), user.getPosition(), o.getCourseTitle(), r.getTestTitle(),
                        o.getBeginDate(), o.getEndDate(), r.getFinishTime(), r.getAmountQuestions(), r.getAmountCorrectAnswers()));
            }
        }
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Group #" + id);
            Row row = null;
            Cell cell = null;

            //styles

            CellStyle styleCenter = workbook.createCellStyle();
            styleCenter.setAlignment(CellStyle.ALIGN_CENTER);
            styleCenter.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            styleCenter.setWrapText(true);

            CellStyle styleLeft = workbook.createCellStyle();
            styleLeft.setAlignment(CellStyle.ALIGN_LEFT);
            styleLeft.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            styleLeft.setWrapText(true);

            CellStyle styleDateWithoutTime = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            styleDateWithoutTime.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            styleDateWithoutTime.setAlignment(CellStyle.ALIGN_LEFT);
            styleDateWithoutTime.setVerticalAlignment(CellStyle.VERTICAL_TOP);

            CellStyle styleDateWithTime = workbook.createCellStyle();
            styleDateWithTime.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy hh:mm"));
            styleDateWithTime.setAlignment(CellStyle.ALIGN_LEFT);
            styleDateWithTime.setVerticalAlignment(CellStyle.VERTICAL_TOP);

            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue((String) "Группа №:");
            cell.setCellStyle(styleLeft);
            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));
            cell = row.createCell(2);
            cell.setCellValue((String) group.getTitle());
            cell.setCellStyle(styleLeft);

            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue((String) "Группа сформирована:");
            cell.setCellStyle(styleLeft);
            sheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));
            cell = row.createCell(2);
            cell.setCellValue(createDate(group.getCreationDate()));
            cell.setCellStyle(styleDateWithoutTime);

            row = sheet.createRow(3);
            cell = row.createCell(0);
            cell.setCellValue((String) "№");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(1);
            cell.setCellValue((String) "Фамилия");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(2);
            cell.setCellValue((String) "Имя");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(3);
            cell.setCellValue((String) "Отчество");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(4);
            cell.setCellValue((String) "Фирма");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(5);
            cell.setCellValue((String) "Должность");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(6);
            cell.setCellValue((String) "Название Курса");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(7);
            cell.setCellValue((String) "Название теста");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(8);
            cell.setCellValue((String) "Дата начала");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(9);
            cell.setCellValue((String) "Дата окончания");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(10);
            cell.setCellValue((String) "Результаты");
            cell.setCellStyle(styleCenter);
            cell = row.createCell(11);
            cell.setCellValue((String) "Тест пройден");
            cell.setCellStyle(styleCenter);

            int currentRow = 5;
            int count = 1;
            for (ResultForExcelListDTO r :
                    results) {
                row = sheet.createRow(currentRow);
                cell = row.createCell(0);
                cell.setCellValue((int) count);
                cell.setCellStyle(styleCenter);
                cell = row.createCell(1);
                cell.setCellValue((String) r.getLastName());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(2);
                cell.setCellValue((String) r.getFirstName());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(3);
                cell.setCellValue((String) r.getMiddleName());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(4);
                cell.setCellValue((String) r.getCompanyTitle());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(5);
                cell.setCellValue((String) r.getPosition());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(6);
                cell.setCellValue((String) r.getCourseTitle());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(7);
                cell.setCellValue((String) r.getTestTitle());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(8);
                cell.setCellValue(createDate(r.getBeginDate()));
                cell.setCellStyle(styleDateWithoutTime);
                cell = row.createCell(9);
                cell.setCellValue(createDate(r.getEndDate()));
                cell.setCellStyle(styleDateWithoutTime);
                cell = row.createCell(10);
                cell.setCellValue((String) r.getAmountCorrectAnswers().toString()+"/"+r.getAmountQuestions());
                cell.setCellStyle(styleLeft);
                cell = row.createCell(11);
                cell.setCellValue(createDateTime(r.getFinishTime()));
                cell.setCellStyle(styleDateWithTime);
                count++;
                currentRow++;
            }

            //autoSizeColumns(workbook);

            sheet.setColumnWidth(0, 1554);
            sheet.setColumnWidth(1, 4200);
            sheet.setColumnWidth(2, 4116);
            sheet.setColumnWidth(3, 4200);
            sheet.setColumnWidth(4, 6594);
            sheet.setColumnWidth(5, 6090);
            sheet.setColumnWidth(6, 6384);
            sheet.setColumnWidth(7, 6384);
            sheet.setColumnWidth(8, 5040);
            sheet.setColumnWidth(9, 5040);
            sheet.setColumnWidth(10, 3150);
            sheet.setColumnWidth(11, 5040);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public XSSFWorkbook getExcelResultByOrderId(int id) {
        ResultDTO result = new ResultDAO().getResultByIdFromDb(id);
        OrderDTO order = new OrderDAO().getOrderByIdFromDb(result.getOrderId());
        UserDTO user = new UserDAO().getUserByIdFromDb(order.getUserId());
        CourseDTO course = new CourseDAO().getCourseByIdFromDb(order.getCourseId());
        ArrayList<UserAnswerDTO> userAnswers = new ResultDAO().getUserAnswersByResultId(result.getId());

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Result #" + id);
            Row row = null;
            Cell cell = null;

            //styles


            CellStyle styleBold = workbook.createCellStyle();
            Font fontBold = workbook.createFont();
            fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontBold.setFontName("Calibri");
            fontBold.setFontHeightInPoints((short) (14));
            styleBold.setFont(fontBold);

            CellStyle styleTable = workbook.createCellStyle();
            styleTable.setAlignment(CellStyle.ALIGN_CENTER);
            styleTable.setBorderBottom(CellStyle.BORDER_MEDIUM);
            styleTable.setBorderTop(CellStyle.BORDER_MEDIUM);
            styleTable.setBorderRight(CellStyle.BORDER_MEDIUM);
            styleTable.setBorderLeft(CellStyle.BORDER_MEDIUM);
            Font fontArial = workbook.createFont();
            fontArial.setFontName("Arial");
            fontArial.setFontHeightInPoints((short) (10));
            styleTable.setFont(fontArial);

            CellStyle styleTableText = workbook.createCellStyle();
            styleTableText.setAlignment(CellStyle.ALIGN_LEFT);
            styleTableText.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            styleTableText.setBorderBottom(CellStyle.BORDER_MEDIUM);
            styleTableText.setBorderTop(CellStyle.BORDER_MEDIUM);
            styleTableText.setBorderRight(CellStyle.BORDER_MEDIUM);
            styleTableText.setBorderLeft(CellStyle.BORDER_MEDIUM);
            styleTableText.setWrapText(true);
            styleTableText.setFont(fontArial);

            CellStyle styleText = workbook.createCellStyle();
            styleText.setFont(fontArial);
            styleText.setAlignment(CellStyle.ALIGN_LEFT);
            styleText.setVerticalAlignment(CellStyle.VERTICAL_TOP);

            CellStyle rowStyle = workbook.createCellStyle();
            rowStyle.setWrapText(true);
            rowStyle.setFont(fontArial);

            CellStyle styleDateWithoutTime = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            styleDateWithoutTime.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            styleDateWithoutTime.setFont(fontArial);
            styleDateWithoutTime.setAlignment(CellStyle.ALIGN_LEFT);
            styleDateWithoutTime.setVerticalAlignment(CellStyle.VERTICAL_TOP);

            CellStyle styleDateWithTime = workbook.createCellStyle();
            styleDateWithTime.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy hh:mm"));
            styleDateWithTime.setFont(fontArial);
            styleDateWithTime.setAlignment(CellStyle.ALIGN_LEFT);
            styleDateWithTime.setVerticalAlignment(CellStyle.VERTICAL_TOP);


            CellStyle styleBoldText = workbook.createCellStyle();
            Font fontBoldArial = workbook.createFont();
            fontBoldArial.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontBoldArial.setFontName("Arial");
            fontBoldArial.setFontHeightInPoints((short) (10));
            styleBoldText.setFont(fontBoldArial);
            styleBoldText.setAlignment(CellStyle.ALIGN_LEFT);
            styleBoldText.setVerticalAlignment(CellStyle.VERTICAL_TOP);


            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue((String) user.getLastName()+" "+user.getFirstName()+" "+user.getMiddleName());
            cell.setCellStyle(styleBold);
            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            row = sheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue((String) "Организация:");
            cell.setCellStyle(styleText);
            cell = row.createCell(1);
            cell.setCellValue((String) user.getCompanyTitle());
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            row = sheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue((String) "Должность:");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellValue((String) user.getPosition());
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    2, //first row (0-based)
                    2, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            row = sheet.createRow(3);
            cell = row.createCell(0);
            cell.setCellValue((String) "Предмет тестирования:");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellValue((String) course.getTitle());
            row.setRowStyle(rowStyle);
            cell = row.createCell(1);
            cell.setCellStyle(rowStyle);

            sheet.addMergedRegion(new CellRangeAddress(
                    3, //first row (0-based)
                    3, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            row = sheet.createRow(4);
            cell = row.createCell(0);
            cell.setCellValue((String) "Дата и время проведения тестирования:");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellValue(createDateTime(result.getStartTime()));
            cell.setCellStyle(styleDateWithTime);

            sheet.addMergedRegion(new CellRangeAddress(
                    4, //first row (0-based)
                    4, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            row = sheet.createRow(5);
            cell = row.createCell(0);
            cell.setCellValue((String) "Номер билета:");
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    5, //first row (0-based)
                    5, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    6, //first row (0-based)
                    6, //last row  (0-based)
                    0, //first column (0-based)
                    3  //last column  (0-based)
            ));

            row = sheet.createRow(7);
            cell = row.createCell(0);
            cell.setCellValue((String) "№");
            cell.setCellStyle(styleTable);
            cell = row.createCell(1);
            cell.setCellValue((String) "Вопрос");
            cell.setCellStyle(styleTable);
            cell = row.createCell(2);
            cell.setCellValue((String) "Ответ");
            cell.setCellStyle(styleTable);
            cell = row.createCell(3);
            cell.setCellValue((String) "Результат");
            cell.setCellStyle(styleTable);

            int currentRow = 8;
            int count = 1;
            for (UserAnswerDTO u:
                    userAnswers) {
                row = sheet.createRow(currentRow);
                cell = row.createCell(0);
                cell.setCellStyle(styleTableText);
                cell.setCellValue((int) count);
                cell = row.createCell(1);
                cell.setCellStyle(styleTableText);
                cell.setCellValue((String) u.getQuestion());
                cell = row.createCell(2);
                cell.setCellStyle(styleTableText);
                cell.setCellValue((String) u.getAnswer());
                cell = row.createCell(3);
                cell.setCellStyle(styleTableText);
                cell.setCellValue((String) u.getIsCorrect());
                count++;
                currentRow++;
            }

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "Допустимое количество ошибок:");
            cell.setCellStyle(styleBoldText);
            cell = row.createCell(2);
            cell.setCellStyle(styleBoldText);

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    2, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "Допущено ошибок:");
            cell.setCellStyle(styleBoldText);
            cell = row.createCell(2);
            cell.setCellValue(result.getAmountQuestions()-result.getAmountCorrectAnswers());
            cell.setCellStyle(styleBoldText);

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    2, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "Результат тестирования: ");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellStyle(styleText);


            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    2, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "При проведении тестирования нарушений его порядка не зафиксировано");
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "Ответственный за проведение тестирования:");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellValue((String) "__________________ /_________________________/");
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    2, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    3  //last column  (0-based)
            ));

            currentRow++;

            row = sheet.createRow(currentRow);
            cell = row.createCell(0);
            cell.setCellValue((String) "Тестируемый");
            cell.setCellStyle(styleText);
            cell = row.createCell(2);
            cell.setCellValue((String) "__________________ /_________________________/");
            cell.setCellStyle(styleText);

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    0, //first column (0-based)
                    1  //last column  (0-based)
            ));

            sheet.addMergedRegion(new CellRangeAddress(
                    currentRow, //first row (0-based)
                    currentRow, //last row  (0-based)
                    2, //first column (0-based)
                    3  //last column  (0-based)
            ));


            sheet.setColumnWidth(0, 1800);
            sheet.setColumnWidth(1, 9200);
            sheet.setColumnWidth(2, 9400);



        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }


//work with date

    private Date createDate(String inputString){
        inputString = inputString.substring(0, 10).replaceAll("-", ".")+inputString.substring(10, 16);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(inputString);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    private Date createDateTime(String inputString){
        inputString = inputString.replaceAll("-", ".")+inputString.substring(10, 16);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date date = null;
        try {
            date = dateFormat.parse(inputString);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    private Date reverseDate(String inputString){
        String s = inputString.substring(8, 10) + "." + inputString.substring(5, 7) + "." + inputString.substring(0, 4)+ " " + inputString.substring(11, 13) + ":" + inputString.substring(14, 16);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date date = null;
        try {
            date = dateFormat.parse(s);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }


    public void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

}