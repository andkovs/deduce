package rest;

import dao.QuestionDAO;
import dao.RoleDAO;
import dao.TokenDAO;
import dao.TokenGenerator;
import dto.ErrorMessageDTO;
import dto.QuestionDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/questions")
public class QuestionService {

    private final QuestionDAO dao = new QuestionDAO();

    @GET
    @Path("/{id}")
    public Response getQuestionById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                QuestionDTO question = dao.getQuestionByIdFromDb(id);
                if (question == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить вопрос.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(question).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewQuestion(@HeaderParam("token") String token, QuestionDTO question) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                QuestionDTO newQuestion = dao.setQuestionInDB(question);
                if (newQuestion == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось сохранить информацию о вопросе.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newQuestion).build();
            }
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateQuestion(@HeaderParam("token") String token, @PathParam("id") int id, QuestionDTO question) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.updateQuestionInDB(id, question);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось изменить информацию о вопросе.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteQuestion(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteQuestionFromDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить вопрос.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().build();
            }
        }
        return Response.status(401).build();
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
