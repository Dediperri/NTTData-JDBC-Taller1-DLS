package com.nttdata.jdbc;

import java.sql.*;
import java.util.Scanner;

/**
 * NTTDATA - JDBC - TALLER1
 * 
 * @author Diego Lopez Strickland
 *
 */
public class App {

	/**
	 * Metodo main de mi aplicacion
	 * 
	 * @param String[] args
	 */
	public static void main(String[] args) {

		// En el metodo main creo un scanner e introduzco los datos de mi usuario y
		// clave
		Scanner input = new Scanner(System.in);
		System.out.println("Insert your user");
		final String user = input.nextLine();
		System.out.println("Insert your password");
		final String pass = input.nextLine();
		final String query = "SELECT nttdata_mysql_soccer_player.name AS playerName, nttdata_mysql_soccer_team.name AS teamName FROM nttdata_mysql_soccer_player JOIN nttdata_mysql_soccer_team ON nttdata_mysql_soccer_player.id_soccer_team = nttdata_mysql_soccer_team.id_soccer_team;";
		stablishJDBCConnection(user, pass, query);

	}

	/**
	 * Metodo estatico y privado que establece la conexion con la base de datos
	 * 
	 * @param final String user
	 * @param final String password
	 * @param final String querySentence
	 */

	private static void stablishJDBCConnection(final String user, final String password, final String querySentence) {

		try {

			// Se establece el driver de conexion a BBDD mysql.
			Class.forName("org.mariadb.jdbc.Driver");

			// Creo la url para conectarme a mi bbdd con mi usuario y mi clave
			StringBuilder url = new StringBuilder();
			url.append("jdbc:mysql://localhost/nttdata_javajdbc?user=");
			url.append(user);
			url.append("&password=");
			url.append(password);

			// Apertura de conexion con BBDD (URL / Usuario / Clave), conexion a mysql
			final Connection dbconnection = DriverManager.getConnection(url.toString());

			// Realización de la consulta
			final Statement sentence = dbconnection.createStatement();
			final ResultSet queryResult = sentence.executeQuery(querySentence);

			// Creo un stringbuilder para pasar por consola el resultado
			StringBuilder playerInfo = new StringBuilder();
			// Iteracion de resultados.
			while (queryResult.next()) {

				// Concateno el resultado en mi playerInfo
				playerInfo.append("Nombre: ");
				playerInfo.append(queryResult.getString("playerName"));

				playerInfo.append(" | Equipo: ");
				playerInfo.append(queryResult.getString("teamName"));
				playerInfo.append("\n");
			}

			// Imprimo por consola
			System.out.println(playerInfo);

			// Cierro la conexion
			dbconnection.close();

		} catch (SQLException | ClassNotFoundException e) {

			// Escribe por consola el fallo
			System.out.println(e);
		}
	}
}
