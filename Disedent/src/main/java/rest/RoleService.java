package rest;

import dao.RoleDAO;
import dao.TokenDAO;
import dao.TokenGenerator;
import dto.ErrorMessageDTO;
import dto.RoleDTO;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/roles")
public class RoleService {

    private final RoleDAO dao = new RoleDAO();

    @GET
    public Response getAllRoles(@HeaderParam("token") String token) throws ParseException {
        String[] allowRoles = {"admin", "student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                ArrayList<String> rs = dao.getAllRoles();
                if (rs == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить " +
                            "информацию о ролях.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(rs).build();
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
