package dao;

import model.Stardew_character;

import java.util.List;

public class CharacterDAO extends DAO {
	public CharacterDAO() {
		super();
		
	}

	public void finalize() {
		close();
	}

	public boolean insert(Stardew_character new_personagem) {
		boolean status = false;

		status = inserirCharacter(new_personagem);

		return status;
	}

	// TO DO - BEGGINING
	public Stardew_character get(int id) {
		
		Stardew_character personagem = getCharacterInfo(id);

		return personagem;
	}

	public List<Stardew_character> get() {
		return getCharacters("");
	}

	public List<Stardew_character> getOrderByID() {
		return getCharacters("id");
	}

	public List<Stardew_character> getOrderByName() {
		return getCharacters("name");
	}

	public List<Stardew_character> getOrderByLives_in() {
		return getCharacters("lives_in");
	}

	public List<Stardew_character> getOrderByAddress() {
		return getCharacters("address");
	}

	public List<Stardew_character> getOrderByBirthday() {
		return getCharacters("birthday");
	}
	// TO DO - END

	public void update(int id, String name, String lives_in, String address, String birthday) {

		Stardew_character personagem = getCharacterInfo(id);
		personagem.setName(name);
		personagem.setLives_in(lives_in);
		personagem.setAddress(address);
		personagem.setBirthday(birthday);

		atualizarCharacter(personagem);

	}

	public void delete(int id) {

		// Excluir usuário
		excluirCharacter(id);
	}
}