package kr.myself.info.controller;

import kr.myself.info.model.Carrer;
import kr.myself.info.model.Education;
import kr.myself.info.model.PersonInfo;
import kr.myself.info.view.ResumeView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ResumeController {

    private ResumeView view; //  화면
    private Workbook workbook; // 메모리 엑셀

    public ResumeController() {
        view = new ResumeView();
        workbook = new XSSFWorkbook();
    }

    private void createResume(){
        PersonInfo personInfo = view.inputPersonInfo();
        List<Education> educations = view.inputEducation();
        List<Carrer> carrers = view.inputCarrer();
        String selfIntroduction = view.inputSelfIntroduction();

        createResumSheet(personInfo,educations,carrers);
        createSelfIntroductionSheet(selfIntroduction);

        saveWorkbookToFile();

        System.out.println("이력서 생성이 완료 되었습니다.");
    }

    private void createResumSheet(PersonInfo personInfo, List<Education> educations, List<Carrer> carrers) {
        Sheet sheet = workbook.createSheet("이력서");

        // 헤더 생성
        Row hearderRow = sheet.createRow(0);
        hearderRow.createCell(0).setCellValue("사진");
        hearderRow.createCell(1).setCellValue("이름");
        hearderRow.createCell(2).setCellValue("이메일");
        hearderRow.createCell(3).setCellValue("주소");
        hearderRow.createCell(4).setCellValue("전화번호");
        hearderRow.createCell(5).setCellValue("생년월일");

        // 데이터 삽입
        Row dataRow = sheet.createRow(1);
        String photoFilename = personInfo.getPhoto();
        try (InputStream photoStream = new FileInputStream(photoFilename)) {
            // 사진 파일을 읽어들입니다.
            BufferedImage originalImage = ImageIO.read(photoStream);

            // 증명사진 크기로 이미지를 조절합니다. (가로 35mm, 세로 45mm)
            int newWidth = (int) (35 * 2.83465);
            int newHeight = (int) (45 * 2.83465);
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage resizeBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = resizeBufferedImage.createGraphics();
            g2d.drawImage(resizedImage,0,0,null);
            g2d.dispose();

            // 조절된 이미지를 바이트 배열로 변환
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizeBufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            int imageIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

            // Drawing 객체를 생성하고 이미지를 삽입합니다.
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 0, 1, 1, 2);
            drawing.createPicture(anchor, imageIndex);

            // 이미지가 삽입된 행의 높이와 열의 너비를 조정합니다.
            // 96은 화면의 DPI(Dots Per Inch, 인치당 도트 수)
            // 엑셀에서 셀의 높이는 포인트 단위로 표시 (1포인트는 1/72 인치입니다.)
            dataRow.setHeightInPoints(newHeight * 72 / 96);
            // 8이란 값은, 엑셀에서 사용되는 기본 문자 폭의 값
            // 엑셀에서는 한 개의 문자가 차지하는 너비를 1/256 단위로 계산
            int columnWidth = (int) Math.floor(((float) newWidth / (float) 8) * 256);
            sheet.setColumnWidth(0, columnWidth);

        } catch (IOException e) {
            e.printStackTrace();
        }

        dataRow.createCell(1).setCellValue(personInfo.getName());
        dataRow.createCell(2).setCellValue(personInfo.getEmail());
        dataRow.createCell(3).setCellValue(personInfo.getAddress());
        dataRow.createCell(4).setCellValue(personInfo.getPhoneNumber());
        dataRow.createCell(5).setCellValue(personInfo.getBirthDate());

        // 학력사항 헤더 생성
        int educationStartRow = 3;
        Row educationHeaderRow = sheet.createRow(educationStartRow - 1);
        educationHeaderRow.createCell(0).setCellValue("졸업년도");
        educationHeaderRow.createCell(1).setCellValue("학교명");
        educationHeaderRow.createCell(2).setCellValue("전공");
        educationHeaderRow.createCell(3).setCellValue("졸업여부");

        // 학력사항 데이터 삽입
        int educationRowNum = educationStartRow;
        for (Education education: educations){
            Row educationDataRow = sheet.createRow(educationRowNum++);
            educationDataRow.createCell(0).setCellValue(education.getGraduationYear());
            educationDataRow.createCell(1).setCellValue(education.getSchoolName());
            educationDataRow.createCell(2).setCellValue(education.getMajor());
            educationDataRow.createCell(3).setCellValue(education.getGraduationSatus());
        }


        // 경력사항 헤더 생성
        int carrerStartRow = educationRowNum + 1;
        Row carrerHeaderRow = sheet.createRow(carrerStartRow - 1);
        carrerHeaderRow.createCell(0).setCellValue("근무기간");
        carrerHeaderRow.createCell(1).setCellValue("근무처");
        carrerHeaderRow.createCell(2).setCellValue("담당업무");
        carrerHeaderRow.createCell(3).setCellValue("근속연수");

        // 학력사항 데이터 삽입
        int careerRowNum = carrerStartRow;
        for (Carrer carrer : carrers){
            Row carrerDataRow = sheet.createRow(careerRowNum++);
            carrerDataRow.createCell(0).setCellValue(carrer.getEmploymentYears());
            carrerDataRow.createCell(1).setCellValue(carrer.getCompanyName());
            carrerDataRow.createCell(2).setCellValue(carrer.getJobTitle());
            carrerDataRow.createCell(3).setCellValue(carrer.getWorkPeriod());
        }
    }

    private void createSelfIntroductionSheet(String selfIntroduction){
        Sheet sheet = workbook.createSheet("자기소개서");

        // 데이터 삽입
        Row dataRow = sheet.createRow(0);
        Cell selfIntroductionCell = dataRow.createCell(0);
        selfIntroductionCell.setCellStyle(getWrapCellStyle());
        selfIntroductionCell.setCellValue(new XSSFRichTextString(selfIntroduction.replaceAll("\n", String.valueOf((char) 10))));

    }
    private XSSFCellStyle getWrapCellStyle(){
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    public void saveWorkbookToFile(){
        try(FileOutputStream fileOut = new FileOutputStream("이력서.xlsx")) {
            workbook.write(fileOut);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ResumeController controller = new ResumeController();
        controller.createResume();

    }

}
