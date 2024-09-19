package service;

import java.util.Scanner;
//import java.time.LocalDate;
import java.io.File;
//import java.time.LocalDateTime;
import java.util.List;
import dao.CharacterDAO;
import model.Stardew_character;
import spark.Request;
import spark.Response;

//import static spark.Spark.*;


public class CharacterService {

	private CharacterDAO CharacterDAO = new CharacterDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NAME = 2;
	private final int FORM_ORDERBY_LIVES_IN = 3;
	private final int FORM_ORDERBY_ADDRESS = 4;
	private final int FORM_ORDERBY_BIRTHDAY = 5;
	
	
	public CharacterService() {
		makeForm();
	}

	public int getNextID() {
		return CharacterDAO.getNextID();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Stardew_character(), FORM_ORDERBY_NAME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Stardew_character(), orderBy);
	}

	
	public void makeForm(int tipo, Stardew_character personagem, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPersonagem = "";
		if(tipo != FORM_INSERT) {
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/character/list/1\">Novo Personagem</a></b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";
			umPersonagem += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/character/";
			String name, nome, lives_in, address, birthday, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Personagem";
				nome = "[Nome]";
				lives_in = "Pelican Town,...";
				address = "Willow Lane,...";
				birthday = "Spring/Summer/... xx";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + personagem.getId();
				name = "Atualizar Personagem (ID " + personagem.getId() + ")";
				nome = personagem.getName();
				lives_in = personagem.getLives_in();
				address = personagem.getAddress();
				birthday = personagem.getBirthday();
				buttonLabel = "Atualizar";
			}
			umPersonagem += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Name: <input class=\"input--register\" type=\"text\" name=\"name\" value=\""+ nome +"\"></td>";
			umPersonagem += "\t\t\t<td>Lives in: <input class=\"input--register\" type=\"text\" name=\"lives_in\" value=\""+ lives_in +"\"></td>";
			umPersonagem += "\t\t\t<td>Address: <input class=\"input--register\" type=\"text\" name=\"address\" value=\""+ address +"\"></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Birthday: <input class=\"input--register\" type=\"text\" name=\"birthday\" value=\""+ birthday + "\"></td>";
			umPersonagem += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button green\"></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";
			umPersonagem += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Personagem (ID " + personagem.getId() + ")</b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Name: "+ personagem.getName() +"</td>";
			umPersonagem += "\t\t\t<td>Lives In: "+ personagem.getLives_in() +"</td>";
			umPersonagem += "\t\t\t<td>Address: "+ personagem.getAddress() +"</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Birthday: "+ personagem.getBirthday() +"</td>";
			umPersonagem += "\t\t\t<td>Birthday: "+ personagem.getBirthday() +"</td>";
			umPersonagem += "\t\t\t<td>&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PERSONAGEM>", umPersonagem);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Personagens</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/character/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/character/list/" + FORM_ORDERBY_NAME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/character/list/" + FORM_ORDERBY_LIVES_IN + "\"><b>Lives In</b></a></td>\n" +
        		"\t<td><a href=\"/character/list/" + FORM_ORDERBY_ADDRESS + "\"><b>Address</b></a></td>\n" +
        		"\t<td><a href=\"/character/list/" + FORM_ORDERBY_BIRTHDAY + "\"><b>Birthday</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Stardew_character> personagens;
		if (orderBy == FORM_ORDERBY_ID) {					personagens = CharacterDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NAME) {			personagens = CharacterDAO.getOrderByName();
		} else if (orderBy == FORM_ORDERBY_LIVES_IN) {		personagens = CharacterDAO.getOrderByLives_in();
		} else if (orderBy == FORM_ORDERBY_ADDRESS) {		personagens = CharacterDAO.getOrderByAddress();
		} else if (orderBy == FORM_ORDERBY_BIRTHDAY) {		personagens = CharacterDAO.getOrderByBirthday();
		} else {											personagens = CharacterDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Stardew_character pS : personagens) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + pS.getId() + "</td>\n" +
            		  "\t<td>" + pS.getName() + "</td>\n" +
            		  "\t<td>" + pS.getLives_in() + "</td>\n" +
            		  "\t<td>" + pS.getAddress() + "</td>\n" +
            		  "\t<td>" + pS.getBirthday() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/character/" + pS.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/character/update/" + pS.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePersonagem('" + pS.getId() + "', '" + pS.getName() + "', '" + pS.getLives_in() + "', '" + pS.getAddress() + "', '" + pS.getBirthday() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PERSONAGEM>", list);				
	}
	
	
	public Object insert(Stardew_character new_character) {

		String resp = "";
	
		if(CharacterDAO.insert(new_character) == true) {
            resp = "Personagem (" + new_character.getName() + ") inserido!";
            //response.status(201); // 201 Created
		} else {
			resp = "Personagem (" + new_character.getName() + ") não inserido!";
			//response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Stardew_character personagem = (Stardew_character) CharacterDAO.get(id);
		
		if (personagem != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, personagem, FORM_ORDERBY_NAME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Personagem " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Stardew_character personagem = (Stardew_character) CharacterDAO.get(id);
		
		if (personagem!= null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, personagem, FORM_ORDERBY_NAME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Personagem " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Stardew_character personagem = (Stardew_character) CharacterDAO.get(id);
        String resp = "";       

        if (personagem != null) {
        	CharacterDAO.update(id, request.queryParams("name"), request.queryParams("lives_in"), request.queryParams("address"), request.queryParams("birthday"));
        	response.status(200); // success
            resp = "Personagem (ID " + personagem.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Personagem (ID \" + personagem.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Stardew_character personagem = (Stardew_character) CharacterDAO.get(id);
        String resp = "";       

        if (personagem != null) {
            CharacterDAO.delete(id);
            response.status(200); // success
            resp = "Personagem (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Personagem (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}