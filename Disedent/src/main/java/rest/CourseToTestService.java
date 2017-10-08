package rest;

import dao.CourseToTestDAO;
import dao.RoleDAO;
import dao.TokenDAO;
import dao.TokenGenerator;
import dto.CourseToTestDTO;
import dto.ErrorMessageDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/courseToTest")
public class CourseToTestService {

    private final CourseToTestDAO dao = new CourseToTestDAO();

    @POST
    public Response setNewCourse(@HeaderParam("token") String token, CourseToTestDTO courseToTest) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.setCourseToTestInDB(courseToTest);
                if(status==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о тесте для курса.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();


    }

    @PUT
    public Response updateCourse(@HeaderParam("token") String token, CourseToTestDTO courseToTest) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteCourseToTestInDB(courseToTest);
                if(status==null){
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить информацию о тесте для курса.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();


    }

    private boolean roleContain(ArrayList<String> roles, String[] allowRoles) {
        for (String a:
                allowRoles) {
            for (String r :
                    roles) {
                if(a.equals(r)){
                    return true;
                }
            }
        }
        return false;
    }
}
