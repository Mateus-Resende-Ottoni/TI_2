
package com.ti2cc;

//import com.ti2cc.Usuario;
//import com.ti2cc.DAO;

public class Principal {

	// Lista de opções
	public static void lista_options() {
		System.out.println("1- Inserir");
		System.out.println("2- Listar");
		System.out.println("3- Atualizar");
		System.out.println("4- Excluir");
		System.out.println("5- Sair");
	}

	// Create character
	public static void create(DAO dao) {

		// Dados
		int id;
		String name;
		String lives_in;
		String address;
		String birthday;

		// Definir dados
		/* */
		System.out.print("Id: ");
		id = MyIO.readInt();
		/* */
		/* */
		System.out.print("Name: ");
		name = MyIO.readLine();
		/* */
		/* */
		System.out.print("Lives in: ");
		lives_in = MyIO.readLine();
		/* */
		/* */
		System.out.print("Address: ");
		address = MyIO.readLine();
		/* */
		/* */
		System.out.print("Birthday Season: ");
		birthday = MyIO.readLine();
		/* */

		// Inserir o elemento na tabela
		Stardew_character usuario = new Stardew_character(id, name, lives_in, address, birthday);
		if (dao.inserirCharacter(usuario) == true) {
			System.out.println("Inserção com sucesso -> " + usuario.toString());
		}

	}

	// Read(show) characters
	public static void read(DAO dao) {

		// Mostrar personagens
		Stardew_character personagens[] = dao.getCharacters();
		System.out.println("====== Stardew Characters ====== ");
		for (int i = 0; i < personagens.length; i++) {
			System.out.println(personagens[i].toString());
		}
		for (int x = 0; x < 3; x++) {
			System.out.println("");
		}
		

	}

	// Update character data
	public static void update(DAO dao) {

		// Dados
		int opcao = 0; int char_id = 1;
		String new_string;

		System.out.print("Digite o id do personagem: ");
		char_id = MyIO.readInt();

		Stardew_character personagem = new Stardew_character();
		personagem = dao.getCharacterInfo(char_id);

		System.out.println("1- Name");
		System.out.println("2- Lives In");
		System.out.println("3- Address");
		System.out.println("4- Birthday");

		System.out.println("");
		System.out.print("Escolha sua opção: ");
		opcao = MyIO.readInt();

		System.out.println("");
		switch (opcao) {
			case 1:
				System.out.print("Name: ");
				new_string = MyIO.readLine();
				personagem.setName(new_string);
				break;
			case 2:
				System.out.print("Lives in: ");
				new_string = MyIO.readLine();
				personagem.setLives_in(new_string);
				break;
			case 3:
				System.out.print("Address: ");
				new_string = MyIO.readLine();
				personagem.setAddress(new_string);
				break;
			case 4:
				System.out.print("Birthday Season: ");
				new_string = MyIO.readLine();
				personagem.setBirthday(new_string);
				break;
			default:
				break;

		}

		// Atualizar os dados do personagem
		dao.atualizarCharacter(personagem);

	}

	// Delete character
	public static void delete(DAO dao) {

		// Dados
		int char_id;
		Stardew_character personagem = new Stardew_character();

		// Determinar o personagem
		System.out.print("Digite o id do personagem: ");
		char_id = MyIO.readInt();
		personagem = dao.getCharacterInfo(char_id);

		// Excluir usuário
		dao.excluirCharacter(personagem.getId());

	}

	// Mains
	public static void main(String[] args) {

		DAO dao = new DAO();

		dao.conectar();

		// Dados
		int opcao = 0;

		while (opcao != 5) {

			lista_options();

			System.out.println("");
			System.out.print("Escolha sua opção: ");
			opcao = MyIO.readInt();

			switch (opcao) {
				case 1:
					create(dao);
					break;
				case 2:
					read(dao);
					break;
				case 3:
					update(dao);
					break;
				case 4:
					delete(dao);
					break;
				default:
					break;
			}

		}

		dao.close();
	}
}