package rest;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import dao.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/files")
public class FileService {

    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://IdeaProjects//quizFiles//";
    //private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/docs/"+ContextName.getName()+"/";
    private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/var/dis/docs/";

    public static final String MY_JERSEY_EXCEL_FILE_XLSX = "MyJerseyExcelFile.xlsx";
    //public static final String EXPORT_FOLDER = "C://IdeaProjects//quizFiles//";
    //public static final String EXPORT_FOLDER = "/var/docs/"+ContextName.getName()+"/";
    public static final String EXPORT_FOLDER = "/var/dis/docs/";


    //Upload a File

    @POST
    @Path("/pdf")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @FormDataParam("token") String token,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("id") Integer id
    ) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = new TopicDAO().updateIsFileInTopicById(id, "y");
                String filePath = getPath(id);
                java.nio.file.Path outputPath = FileSystems.getDefault().getPath(filePath);
                try {
                    Files.copy(fileInputStream, outputPath);
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/pdf/{id}")
    @Produces("application/pdf")
    public Response getPDF(@HeaderParam("token") String token, @PathParam("id") Integer id) throws ParseException {
        String[] allowRoles = {"admin", "student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                File file = new File(getPath(id));
                Response.ResponseBuilder response = Response.ok((Object) file);
                response.header("Content-Disposition",
                        "attachment; filename=" + id);
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return response.build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/pdf/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFile(@HeaderParam("token") String token, @PathParam("id") Integer id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = new TopicDAO().updateIsFileInTopicById(id, "n");
                File file = new File(getPath(id));
                file.delete();
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/excel/results/{id}")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response getExcelResultsByGroupId(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                File file = new File(EXPORT_FOLDER + MY_JERSEY_EXCEL_FILE_XLSX);
                Response.ResponseBuilder response = null;
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    XSSFWorkbook workbook = new FileDAO().getExcelResultsByGroupId(id);
                    workbook.write(out);
                    out.close();
                    response = Response.ok((Object) file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.header("Content-Disposition", "attachment; filename=\"MyJerseyExcelFile.xlsx\"");
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return response.build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/excel/result/{id}")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response getExcelResultByOrderId(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                File file = new File(EXPORT_FOLDER + MY_JERSEY_EXCEL_FILE_XLSX);
                Response.ResponseBuilder response = null;
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    XSSFWorkbook workbook = new FileDAO().getExcelResultByOrderId(id);
                    workbook.write(out);
                    out.close();
                    response = Response.ok((Object) file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.header("Content-Disposition", "attachment; filename=\"MyJerseyExcelFile.xlsx\"");
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return response.build();
            }
        }
        return Response.status(401).build();
    }

    private String getPath(Integer id) {
        Connection connection = ConnectionToMySQLDB.getConnaction();
        Integer courseId = null;
        Integer chapterId = null;
        if (connection == null) {
            return null;
        }
        try {
            String sql = "select chapterId from loginappdb.topics WHERE topics.topicId = ?";
            PreparedStatement psql = connection.prepareStatement(sql);
            psql.setInt(1, id);
            ResultSet rs = psql.executeQuery();
            while (rs.next()) {
                chapterId = rs.getInt("chapterId");
            }
            sql = "select courseId from loginappdb.chapters WHERE chapterId = ?";
            psql = connection.prepareStatement(sql);
            psql.setInt(1, chapterId);
            rs = psql.executeQuery();
            while (rs.next()) {
                courseId = rs.getInt("courseId");
            }
            rs.close();
            psql.close();
            connection.close();
        } catch (SQLException e) {
            return null;
        }
        //return SERVER_UPLOAD_LOCATION_FOLDER + courseId+"//"+chapterId+"//"+id+".pdf";
        return SERVER_UPLOAD_LOCATION_FOLDER + courseId + "/" + chapterId + "/" + id + ".pdf";
    }

    private boolean roleContain(ArrayList<String> roles, String[] allowRoles) {
        for (String a :
                allowRoles) {
            for (String r :
                    roles) {
                if (a.equals(r)) {
                    return true;
                }
            }
        }
        return false;
    }
}
