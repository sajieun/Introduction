package kr.myself.info.view;

import kr.myself.info.model.Carrer;
import kr.myself.info.model.Education;
import kr.myself.info.model.PersonInfo;

import java.util.Scanner;

public class ResumeView {
    private Scanner scanner;
    public ResumeView(){
        scanner = new Scanner(System.in);
    }

    public PersonInfo inputPersonInfo(){
        System.out.print("사진 파일명을 입력하세요:");
        String photo = scanner.nextLine();

        System.out.print("이름을 입력하세요:");
        String name = scanner.nextLine();

        System.out.print("이메일을 입력하세요:");
        String email = scanner.nextLine();

        System.out.print("주소를 입력하세요:");
        String address = scanner.nextLine();

        System.out.print("전화번호를 입력하세요:");
        String phoneNumber = scanner.nextLine();

        System.out.print("생년월일을 입력하세요: (예: 1997-06-20)");
        String birthDate = scanner.nextLine();

        return new PersonInfo(photo,name,email,address,phoneNumber,birthDate);
    }

    public Education inputEducation() {
        Education education = null;

        while (true) {
            System.out.print("종료하려면 'q'를 입력하세요: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
            System.out.print("졸업년도를 입력하세요:");
            int graduationYear = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            System.out.print("학교 이름을 입력하세요:");
            String schoolName = scanner.nextLine();

            System.out.print("전공을 입력하세요:");
            String major = scanner.nextLine();

            System.out.print("졸업 여부를 입력하세요 (예: 졸업 or 수료):");
            String graduationStatus = scanner.nextLine();

            education = new Education(graduationYear, schoolName, major, graduationStatus);
        }

        return education;
    }


    public Carrer inputCarrer(){
        Carrer carrer = null;

        while (true){
            System.out.print("종료하려면 'q'를 입력하세요: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }

            System.out.print("근무기간을 입력하세요:");
            String workPeriod = scanner.nextLine();

            System.out.print("근무처를 입력하세요:");
            String companyName = scanner.nextLine();

            System.out.print("담당업무를 입력하세요:");
            String jobTitle = scanner.nextLine();

            System.out.print("근속연수를 입력하세요:");
            String employmentYears = scanner.nextLine();

            carrer = new Carrer(workPeriod,companyName,jobTitle,employmentYears);
        }
        return carrer;
    }

    public String inputSelfIntroduction(){
        System.out.println("자기소개를 입력하세요");
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = scanner.nextLine()).trim().length()>0){
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }
}
