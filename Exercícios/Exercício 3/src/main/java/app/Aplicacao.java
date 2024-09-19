package app;

import static spark.Spark.*;

import model.Stardew_character;
import service.CharacterService;


public class Aplicacao {
	
	private static CharacterService characterService = new CharacterService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/character/insert", (request, response) -> {
            String name = request.queryParams("name");
            String lives_in = request.queryParams("lives_in");
            String address = request.queryParams("address");
            String birthday = request.queryParams("birthday");

            Stardew_character character = new Stardew_character(characterService.getNextID(), name, lives_in, address, birthday);

            characterService.insert(character);
            //characterService.insert(request, response));

            return "Character inserted successfully!";
        });

        get("/character/:id", (request, response) -> characterService.get(request, response));
        
        get("/character/list/:orderby", (request, response) -> characterService.getAll(request, response));

        get("/character/update/:id", (request, response) -> characterService.getToUpdate(request, response));
        
        post("/character/update/:id", (request, response) -> characterService.update(request, response));
           
        get("/character/delete/:id", (request, response) -> characterService.delete(request, response));

             
    }
}