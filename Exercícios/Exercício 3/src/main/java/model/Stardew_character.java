package model;

public class Stardew_character {
	private int id;
	private String name;
	private String lives_in;
	private String address;
	private String birthday;


	/* Construtores */
	public Stardew_character() {
		this.id = -1;
		this.name = "A";
		this.lives_in = "None";
		this.address = "None";
		this.birthday = "Spring 1";
	}

	public Stardew_character(int id, String nome, String local, String endereco, String aniversario) {
		this.id = id;
		this.name = nome;
		this.lives_in = local;
		this.address = endereco;
		this.birthday = aniversario;
	}
	/* */


	/* id */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/* */


	/* name */
	public String getName() {
		return this.name;
	}

	public void setName(String nome) {
		this.name = nome;
	}
	/* */


	/* lives_in */
	public String getLives_in() {
		return this.lives_in;
	}

	public void setLives_in(String lives_in) {
		this.lives_in = lives_in;
	}
	/* */


	/* address */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	/* */


	/* birthday */
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/* */




	@Override
	public String toString() {
		return "Stardew_character [Id = " + id + ", Name = " + name + ", Lives in = " + lives_in + ", Address = " + address + ", Birthday = "
				+ birthday + "]";
	}
}