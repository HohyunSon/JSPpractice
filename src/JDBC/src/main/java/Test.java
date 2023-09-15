package JDBC.src.main.java;

import java.sql.*;
import java.util.Scanner;

class Test
{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project?serverTimezone=UTC";
        String id = "root";
        String pw = "wkdrns98";
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        String sql = null,sql2 = null;
        String name = null, title = null, contents = null;
        Scanner sc = new Scanner(System.in);
        int menu = 0;
        int result = 0;

        ResultSet rs = null;
        int no = 0, rCnt = 0;
        String wTime = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,id,pw);
            System.out.println("객체 생성 성공");
            sql = "INSERT INTO board(name,title,contents) VALUES(?,?,?)";
            sql2 = "UPDATE BOARD SET contents = ? WHERE no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sql2);

            while(true)
            {
                System.out.println("메뉴를 선택하세요.");
                System.out.println("1. 쓰기");
                System.out.println("2. 조회");
                System.out.println("3. 수정");
                System.out.println("4. 삭제");
                System.out.println("5. 종료");
                System.out.print("선택 : ");
                menu = sc.nextInt();
                sc.nextLine();
                switch (menu)
                {
                    case 1:
                        System.out.println("이름입력");
                        name = sc.nextLine();
                        System.out.println("제목입력");
                        title = sc.nextLine();
                        System.out.println("내용입력");
                        contents = sc.nextLine();
                        pstmt.setString(1,name);
                        pstmt.setString(2,title);
                        pstmt.setString(3,contents);
                        result = pstmt.executeUpdate();
                        if(result>0)
                            System.out.println(result + "입력 완료");
                        break;
                    case 2:
                        sql = "SELECT * FROM BOARD ORDER BY no DESC";
                        rs = pstmt.executeQuery(sql);
                        while(rs.next())
                        {
                            no = rs.getInt("no");
                            name = rs.getString("name");
                            title = rs.getString("title");
                            contents = rs.getString("contents");
                            wTime = rs.getString("wTime");
                            rCnt = rs.getInt("rCnt");
                            System.out.println("번호 "+no);
                            System.out.println("이름 "+name);
                            System.out.println("제목 "+title);
                            System.out.println("내용 "+contents);
                            System.out.println("시간 "+wTime);
                            System.out.println("조회수 "+rCnt);
                            System.out.println();
                        }
                        break;
                    case 3:
                        System.out.println("수정할 번호 입력");
                        no = sc.nextInt();
                        sc.nextLine();
                        System.out.println("수정할 내용 입력");
                        contents = sc.nextLine();
                        pstmt2.setString(1,contents);
                        pstmt2.setInt(2,no);
                        result = pstmt2.executeUpdate();
                        if(result>0)
                            System.out.println(result+ "수정 완료");
                        break;
                    case 4:
                        System.out.println("삭제할 번호 입력");
                        no = sc.nextInt();
                        sc.nextLine();
                        sql = "DELETE FROM BOARD WHERE no ="+no;
                        result = pstmt.executeUpdate(sql);
                        if(result > 0 )
                            System.out.println(result + "삭제완료");
                        break;
                    case 5:
                        System.out.println("프로그램을 종료합니다..");
                        return;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                        break;
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("객체 생성 실패");
        }finally {
            try{
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                System.out.println("conn.close() 성공");
                // Connection 객체를 닫는다.
                if (sc != null) sc.close();
            } catch (SQLException e) {
                System.out.println("conn.close() 에러");
            }
        }
    }
}