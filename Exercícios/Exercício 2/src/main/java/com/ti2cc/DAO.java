
package com.ti2cc;

//import com.ti2cc.Usuario;

import java.sql.*;

public class DAO {
	private Connection conexao;

	public DAO() {
		conexao = null;
	}

	/* Conectar à base de dados */
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	/* */

	/* Fechar conexão */
	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	/* */

	/* C - Create */
	public boolean inserirCharacter(Stardew_character character) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate(
					"INSERT INTO stardew_character (id, name, lives_in, address, birthday) "
							+ "VALUES (" + character.getId() + ", '" + character.getName() + "', '" + character.getLives_in()
							+ "', '" + character.getAddress() + "', '" + character.getBirthday() +  "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	/* */

	/* R - Read */
	public Stardew_character getCharacterInfo(int char_id) {
	Stardew_character[] personagens = null;

	try {
		Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = st.executeQuery("SELECT * FROM stardew_character WHERE stardew_character.id=" + char_id + "");
		if (rs.next()) {
			rs.last();
			personagens = new Stardew_character[rs.getRow()];
			rs.beforeFirst();

			for (int i = 0; rs.next(); i++) {
				personagens[i] = new Stardew_character(rs.getInt("id"), rs.getString("name"),
						rs.getString("lives_in"), rs.getString("address"),
						rs.getString("birthday"));
			}
		}
		st.close();
	} catch (Exception e) {
		System.err.println(e.getMessage());
	}
	return personagens[0];
}

	public Stardew_character[] getCharacters() {
		Stardew_character[] personagens = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM stardew_character");
			if (rs.next()) {
				rs.last();
				personagens = new Stardew_character[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					personagens[i] = new Stardew_character(rs.getInt("id"), rs.getString("name"),
							rs.getString("lives_in"), rs.getString("address"),
							rs.getString("birthday"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return personagens;
	}


	/* */

	/* U - Update */
	public boolean atualizarCharacter(Stardew_character character) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE stardew_character SET name = '" + character.getName() + "', lives_in = '"
					+ character.getLives_in() + "', address = '" + character.getAddress() + "', birthday = '"
					+ character.getBirthday() + "'" + " WHERE id = " + character.getId() + ";";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	/* */

	/* D - Delete */
	public boolean excluirCharacter(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM stardew_character WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	/* */

}