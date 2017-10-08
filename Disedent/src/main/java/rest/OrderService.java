package rest;

import dao.*;
import dto.ErrorMessageDTO;
import dto.OrderDTO;
import dto.OrderForListDTO;
import dto.OrderUserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/orders")
public class OrderService {

    private final OrderDAO dao = new OrderDAO();

    @GET
    @Path("/{id}")
    public Response getOrderById(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                OrderDTO order = dao.getOrderByIdFromDb(id);
                if (order == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить заказ.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(order).build();
            }
        }
        return Response.status(401).build();
    }

    @GET
    @Path("/user/{id}")
    public Response getOrderByUserId(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"student"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                Integer idByToken = new UserDAO().getUserIdByLToken(token);
                if(idByToken!=id){
                    return Response.status(401).build();
                }
                ArrayList<OrderUserDTO> orders = dao.getOrderByUserIdFromDb(id);
                if (orders == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось получить заказ.");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(orders).build();
            }
        }
        return Response.status(401).build();
    }

    @POST
    public Response setNewOrder(@HeaderParam("token") String token, OrderDTO order) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                OrderForListDTO newOrder = dao.setOrderInDB(order);
                if (newOrder == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось создать заказ." +
                            "Возможно у пользователя уже есть этот курс на этот временной период!");
                    return Response.status(400).entity(error).build();
                }
                new TokenDAO().updateTokenTimeByTokenInDB(token);
                return Response.ok().entity(newOrder).build();
            }
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@HeaderParam("token") String token, @PathParam("id") int id) throws ParseException {
        String[] allowRoles = {"admin"};
        if (token == null) {
            return Response.status(401).build();
        }
        if (TokenGenerator.checkToken(token) && TokenGenerator.checkTokenTime(token)) {
            ArrayList<String> roles = new RoleDAO().getRolesByToken(token);
            if (roleContain(roles, allowRoles)) {
                String status = dao.deleteOrderFromDB(id);
                if (status == null) {
                    ErrorMessageDTO error = new ErrorMessageDTO("Не удалось удалить заказ.");
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
