package net.javatutorial.tutorials;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.javatutorial.tutorials.model.*;

import java.io.IOException;
import java.sql.DriverManager;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServlet extends HttpServlet {

	private static final long serialVersionUID = -4751096228274971485L;

	@Override
	protected void doGet(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
		String tmp = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		List<String> parsedURI = new ArrayList<>(Arrays.asList(reqest.getRequestURI().replaceAll("/$", "").replaceAll("^/", "").split("/")));
		parsedURI.remove(0);
		Integer sizeURI = parsedURI.size();
		db database = new db();


		if(sizeURI.equals(1)){
			String el0 = parsedURI.get(0).toLowerCase();
			if(el0.equals("league")){
				tmp = objectMapper.writeValueAsString(database.getAllLeagues());
			}
			else if(el0.equals("club")){
				tmp = objectMapper.writeValueAsString(database.getAllClubs());
			}
			else if(el0.equals("passport")){
				tmp = objectMapper.writeValueAsString(database.getAllPassports());
			}
			else if(el0.equals("identitycard")){
				tmp = objectMapper.writeValueAsString(database.getAllIdentityCards());
			}
			else if(el0.equals("player")){
				tmp = objectMapper.writeValueAsString(database.getAllPlayers());
			}
		}
		else if(sizeURI.equals(2)){
			String el0 = parsedURI.get(0).toLowerCase();
			if(el0.equals("league")){
				tmp = objectMapper.writeValueAsString(database.findByID(League.class, Integer.parseInt(parsedURI.get(1))));
			}
			else if(el0.equals("club")){
				tmp = objectMapper.writeValueAsString(database.findByID(Club.class, Integer.parseInt(parsedURI.get(1))));
			}
			else if(el0.equals("passport")){
				tmp = objectMapper.writeValueAsString(database.findByID(Passport.class, parsedURI.get(1)));
			}
			else if(el0.equals("identitycard")){
				tmp = objectMapper.writeValueAsString(database.findByID(IdentityCard.class, parsedURI.get(1)));
			}
			else if(el0.equals("player")){
				tmp = objectMapper.writeValueAsString(database.findByID(Player.class, Integer.parseInt(parsedURI.get(1))));
			}
		}
		database.close();
        //response.addHeader("Access-Control-Allow-Origin", "*");
        //response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        //response.addHeader("Access-Control-Allow-Credentials", "true");
        //response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
		response.getWriter().println(tmp);
	}

	@Override
	protected void doDelete(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
		String tmp = "";
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		List<String> parsedURI = new ArrayList<>(Arrays.asList(reqest.getRequestURI().replaceAll("/$", "").replaceAll("^/", "").split("/")));
		parsedURI.remove(0);
		Integer sizeURI = parsedURI.size();
		db database = new db();
		int resp = 1;

		if (sizeURI == 2) {
			if (parsedURI.get(0).equals("club")) {
				database.remove(database.findByID(Club.class, Integer.valueOf(parsedURI.get(1))));
				if (database.findByID(Club.class, Integer.valueOf(parsedURI.get(1))) != null)
					resp = -1;
			} else if (parsedURI.get(0).equals("league")) {
				database.remove(database.findByID(League.class, Integer.valueOf(parsedURI.get(1))));
				if (database.findByID(League.class, Integer.valueOf(parsedURI.get(1))) != null)
					resp = -1;
			} else if (parsedURI.get(0).equals("player")) {
				database.remove(database.findByID(Player.class, Integer.valueOf(parsedURI.get(1))));
				if (database.findByID(Player.class, Integer.valueOf(parsedURI.get(1))) != null)
					resp = -1;
			} else if (parsedURI.get(0).equals("passport")) {
				database.remove(database.findByID(Passport.class, String.valueOf(parsedURI.get(1))));
				if (database.findByID(Passport.class, String.valueOf(parsedURI.get(1))) != null)
					resp = -1;
			} else if (parsedURI.get(0).equals("identitycard")) {
				database.remove(database.findByID(IdentityCard.class, String.valueOf(parsedURI.get(1))));
				if (database.findByID(IdentityCard.class, String.valueOf(parsedURI.get(1))) != null)
					resp = -1;
			}
			if(resp == -1) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("NOT REMOVED");
			}else{
				response.sendError(HttpServletResponse.SC_ACCEPTED);
				response.getWriter().println("REMOVED");

			}
		} else if (sizeURI == 1) {
			if (parsedURI.get(0).equals("player")) {
				database.truncate("player");
			}
			else if (parsedURI.get(0).equals("club")) {
				database.truncate("club");
			}
			else if (parsedURI.get(0).equals("passport")) {
				database.truncate("passport");
			}
			else if (parsedURI.get(0).equals("identitycard")) {
				database.truncate("identitycard");
			}
			else if (parsedURI.get(0).equals("league")) {
				database.truncate("league");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
		int tmp = -1;
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		List<String> parsedURI = new ArrayList<>(Arrays.asList(reqest.getRequestURI().replaceAll("/$", "").replaceAll("^/", "").split("/")));
		parsedURI.remove(0);
		Integer sizeURI = parsedURI.size();
		db database = new db();
		int resp = 1;
		Map<String, String[]> params = reqest.getParameterMap();
		//String str = Arrays.toString(params.get("test")).replaceAll("\\[", "").replaceAll("\\]","");;
		//String str2 = Arrays.toString(params.get("123")).replaceAll("\\[", "").replaceAll("\\]","");;
		//response.getWriter().println(str);
		//response.getWriter().println(str2);
		response.getWriter().println("URI size:");
		response.getWriter().println(sizeURI);

		try{
			if(sizeURI == 1){
				if(parsedURI.get(0).toLowerCase().equals("club")){
					Club club = new Club();
					int leagueID;
					String name;
					League league;

					if(params.get("TEAM_NAME").length == 1 && params.get("LEAGUE_ID").length == 1){
						name = String.valueOf(params.get("TEAM_NAME")[0]);
						leagueID = Integer.valueOf(params.get("LEAGUE_ID")[0]);

						if((league = (League) db.findByID(League.class, leagueID)) != null){
							club.setTeamName(name);
							club.setLeagueDes(league);
							tmp = database.insert(club);
						}
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("player")){
					Player player = new Player();
					int  clubid;
					String firstname, lastname, icserialnumber, passportnumber;
					Club club;
					IdentityCard identityCard;
					Passport passport;

					if(params.get("P_FIRSTNAME").length == 1 && params.get("P_LASTNAME").length == 1 && params.get("PASSPORT_NUMBER").length == 1 && params.get("IDENTITYCARD_NUMBER").length == 1 && params.get("CLUB_ID").length == 1){
						clubid = Integer.valueOf(params.get("CLUB_ID")[0]);
						firstname = String.valueOf(params.get("P_FIRSTNAME")[0]);
						lastname = String.valueOf(params.get("P_LASTNAME")[0]);
						icserialnumber = String.valueOf(params.get("IDENTITYCARD_NUMBER")[0]);
						passportnumber = String.valueOf(params.get("PASSPORT_NUMBER")[0]);

						if((club = (Club) db.findByID(Club.class, clubid)) != null && (identityCard = (IdentityCard) db.findByID(IdentityCard.class, icserialnumber)) != null && (passport = (Passport) db.findByID(Passport.class, passportnumber)) != null){
							player.setClubDes(club);
							player.setFirstName(firstname);
							player.setLastName(lastname);
							player.setIdentityCardDetails(identityCard);
							player.setPassportDetails(passport);
							tmp = database.insert(player);
						}
					}

				}
				else if(parsedURI.get(0).toLowerCase().equals("league")){
					League league = new League();
					String name, country;

					if(params.get("LEAGUE_NAME").length == 1 && params.get("COUNTRY").length == 1){
						name = String.valueOf(params.get("LEAGUE_NAME")[0]);
						country = String.valueOf(params.get("COUNTRY")[0]);
						league.setLeagueName(name);
						league.setCountry(country);
						tmp = database.insert(league);
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("passport")){
					Passport passport = new Passport();
					String kodpanstwa, passportnumber;

					if(params.get("PASSPORT_NUMBER").length == 1 && params.get("KOD_PANSTWA").length == 1 && params.get("dataWydania").length == 1){
						kodpanstwa = String.valueOf(params.get("KOD_PANSTWA")[0]);
						passportnumber = String.valueOf(params.get("PASSPORT_NUMBER")[0]);
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);


						passport.setDataWydania(dataWydania);
						passport.setKodPanstwa(kodpanstwa);
						passport.setPassportNumber(passportnumber);
						database.insert(passport);
						tmp = -33;
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("identitycard")){
					IdentityCard identityCard = new IdentityCard();
					String icnumber;

					if(params.get("IDENTITYCARD_NUMBER").length == 1 && params.get("dataWydania").length == 1){
						icnumber = String.valueOf(params.get("IDENTITYCARD_NUMBER")[0]);
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);

						identityCard.setDataWydania(dataWydania);
						identityCard.setserialNumber(icnumber);
						database.insert(identityCard);
						tmp = -33;
					}
				}
			}
			else if(sizeURI == 2){
				if(parsedURI.get(0).toLowerCase().equals("club")){
					Club club = new Club();
					int leagueID;
					String name;
					League league;

					if(params.get("TEAM_NAME").length == 1 && params.get("LEAGUE_ID").length == 1){
						name = String.valueOf(params.get("TEAM_NAME")[0]);
						leagueID = Integer.valueOf(params.get("LEAGUE_ID")[0]);

						if((league = (League) db.findByID(League.class, leagueID)) != null){
							club.setTeamName(name);
							club.setLeagueDes(league);
							tmp = database.insert(club);
						}
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("player")){
					Player player = new Player();
					int  clubid;
					String firstname, lastname, icserialnumber, passportnumber;
					Club club;
					IdentityCard identityCard;
					Passport passport;

					if(params.get("P_FIRSTNAME").length == 1 && params.get("P_LASTNAME").length == 1 && params.get("PASSPORT_NUMBER").length == 1 && params.get("IDENTITYCARD_NUMBER").length == 1 && params.get("CLUB_ID").length == 1){
						clubid = Integer.valueOf(params.get("CLUB_ID")[0]);
						firstname = String.valueOf(params.get("P_FIRSTNAME")[0]);
						lastname = String.valueOf(params.get("P_LASTNAME")[0]);
						icserialnumber = String.valueOf(params.get("IDENTITYCARD_NUMBER")[0]);
						passportnumber = String.valueOf(params.get("PASSPORT_NUMBER")[0]);

						if((club = (Club) db.findByID(Club.class, clubid)) != null && (identityCard = (IdentityCard) db.findByID(IdentityCard.class, icserialnumber)) != null && (passport = (Passport) db.findByID(Passport.class, passportnumber)) != null){
							player.setClubDes(club);
							player.setFirstName(firstname);
							player.setLastName(lastname);
							player.setIdentityCardDetails(identityCard);
							player.setPassportDetails(passport);
							tmp = database.insert(player);
						}
					}

				}
				else if(parsedURI.get(0).toLowerCase().equals("league")){
					League league = new League();
					String name, country;

					if(params.get("LEAGUE_NAME").length == 1 && params.get("COUNTRY").length == 1){
						name = String.valueOf(params.get("LEAGUE_NAME")[0]);
						country = String.valueOf(params.get("COUNTRY")[0]);
						league.setLeagueid(Integer.parseInt(parsedURI.get(1)));
						league.setLeagueName(name);
						league.setCountry(country);
						tmp = database.insert(league);
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("passport")){
					Passport passport = new Passport();
					String kodpanstwa, passportnumber;

					if(params.get("KOD_PANSTWA").length == 1 && params.get("dataWydania").length == 1){
						kodpanstwa = String.valueOf(params.get("KOD_PANSTWA")[0]);
						passportnumber = String.valueOf(parsedURI.get(1));
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);


						passport.setDataWydania(dataWydania);
						passport.setKodPanstwa(kodpanstwa);
						passport.setPassportNumber(passportnumber);
						database.insert(passport);
						tmp = -33;
					}
				}
				else if(parsedURI.get(0).toLowerCase().equals("identitycard")){
					IdentityCard identityCard = new IdentityCard();
					String icnumber;
					//params.get("IDENTITYCARD_NUMBER").length == 1 &&
					if(params.get("dataWydania").length == 1){
						icnumber = String.valueOf(parsedURI.get(1));
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);

						identityCard.setDataWydania(dataWydania);
						identityCard.setserialNumber(icnumber);
						database.insert(identityCard);
						tmp = -33;
					}
				}
			}
		}catch (Exception e){
			System.out.println(e.getLocalizedMessage());
			database.close();
		}
		database.close();
		if(tmp == -1)
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		else if(tmp == -33){
			response.sendError(201);
		}
		else {
			response.sendError(201);
		}

	}

	@Override
	protected void doPut(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
		int tmp = -1;
		ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		List<String> parsedURI = new ArrayList<>(Arrays.asList(reqest.getRequestURI().replaceAll("/$", "").replaceAll("^/", "").split("/")));
		parsedURI.remove(0);
		Integer sizeURI = parsedURI.size();
		db database = new db();
		int resp = 1;
		Map<String, String[]> params = reqest.getParameterMap();
		//String str = Arrays.toString(params.get("test")).replaceAll("\\[", "").replaceAll("\\]","");;
		//String str2 = Arrays.toString(params.get("123")).replaceAll("\\[", "").replaceAll("\\]","");;
		//response.getWriter().println(str);
		//response.getWriter().println(str2);
		response.getWriter().println("URI size:");
		response.getWriter().println(sizeURI);

		try{
			if(parsedURI.get(0).toLowerCase().equals("club")){
				Club club;
				League league;

				if((club = (Club) database.findByID(Club.class, Integer.valueOf(parsedURI.get(1)))) == null){
					doPost(reqest, response);
					tmp = -2;
				}else{
					if(params.get("TEAM_NAME") != null && params.get("TEAM_NAME").length == 1)
						club.setTeamName(params.get("TEAM_NAME")[0]);
					if(params.get("LEAGUE_ID") != null && params.get("LEAGUE_ID").length == 1)
						if((league = (League) database.findByID(League.class, Integer.valueOf(params.get("LEAGUE_ID")[0]))) != null)
							club.setLeagueDes(league);
					database.merge(club);
					tmp = Integer.valueOf(parsedURI.get(1));
				}
			}
			else if(parsedURI.get(0).toLowerCase().equals("league")){
				League league;

				if((league = (League) database.findByID(League.class, Integer.valueOf(parsedURI.get(1)))) == null){
					doPost(reqest, response);
					tmp = -2;
				}else{
					if(params.get("LEAGUE_NAME") != null && params.get("LEAGUE_NAME").length == 1)
						league.setLeagueName(params.get("LEAGUE_NAME")[0]);
					if(params.get("COUNTRY") != null && params.get("COUNTRY").length == 1)
						league.setCountry(params.get("COUNTRY")[0]);
					database.merge(league);
					tmp = Integer.valueOf(parsedURI.get(1));
				}
			}
			else if(parsedURI.get(0).toLowerCase().equals("passport")){
				Passport passport;

				if((passport = (Passport) database.findByID(Passport.class, String.valueOf(parsedURI.get(1)))) == null){
					doPost(reqest, response);
					tmp = -2;
				}else{
					if(params.get("PASSPORT_NUMBER") != null && params.get("PASSPORT_NUMBER").length == 1)
						passport.setPassportNumber(params.get("PASSPORT_NUMBER")[0]);
					if(params.get("KOD_PANSTWA") != null && params.get("KOD_PANSTWA").length == 1)
						passport.setKodPanstwa(params.get("KOD_PANSTWA")[0]);
					if(params.get("dataWydania") != null && params.get("dataWydania").length == 1) {
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);

						passport.setDataWydania(dataWydania);
					}
					database.merge(passport);
					tmp = -33;
				}
			}
			else if(parsedURI.get(0).toLowerCase().equals("identitycard")){
				IdentityCard identityCard;

				if((identityCard = (IdentityCard) database.findByID(IdentityCard.class, String.valueOf(parsedURI.get(1)))) == null){
					doPost(reqest, response);
					tmp = -2;
				}else{
					if(params.get("IDENTITYCARD_NUMBER") != null && params.get("IDENTITYCARD_NUMBER").length == 1)
						identityCard.setserialNumber(params.get("IDENTITYCARD_NUMBER")[0]);
					if(params.get("dataWydania") != null && params.get("dataWydania").length == 1) {
						String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS";
						DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
						ZonedDateTime dataWydania = ZonedDateTime.parse(params.get("dataWydania")[0].replace("+"," "), Parser);

						identityCard.setDataWydania(dataWydania);
					}
					database.merge(identityCard);
					tmp = -33;
				}
			}
			if(parsedURI.get(0).toLowerCase().equals("player")){
				Player player;
				Club club;
				Passport passport;
				IdentityCard identityCard;

				if((player = (Player) database.findByID(Player.class, Integer.valueOf(parsedURI.get(1)))) == null){
					doPost(reqest, response);
					tmp = -2;
				}else{
					if(params.get("P_FIRSTNAME") != null && params.get("P_FIRSTNAME").length == 1)
						player.setFirstName(params.get("P_FIRSTNAME")[0]);
					if(params.get("P_LASTNAME") != null && params.get("P_LASTNAME").length == 1)
						player.setLastName(params.get("P_LASTNAME")[0]);
					if(params.get("LEAGUE_ID") != null && params.get("LEAGUE_ID").length == 1)
						if((club = (Club) database.findByID(League.class, Integer.valueOf(params.get("CLUB_ID")[0]))) != null)
							player.setClubDes(club);

					if(params.get("PASSPORT_NUMBER") != null && params.get("PASSPORT_NUMBER").length == 1)
						if((passport = (Passport) database.findByID(Passport.class, String.valueOf(params.get("PASSPORT_NUMBER")[0]))) != null)
							player.setPassportDetails(passport);

					if(params.get("IDENTITYCARD_NUMBER") != null && params.get("IDENTITYCARD_NUMBER").length == 1)
						if((identityCard = (IdentityCard) database.findByID(IdentityCard.class, String.valueOf(params.get("IDENTITYCARD_NUMBER")[0]))) != null)
							player.setIdentityCardDetails(identityCard);

					database.merge(player);
					tmp = Integer.valueOf(parsedURI.get(1));
				}
			}

		}catch (Exception e){
			System.out.println(e.getLocalizedMessage());
		}

		if(tmp == -1)
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		else if (tmp == -2){
			;
		}
		else if (tmp == -33){
			response.sendError(HttpServletResponse.SC_ACCEPTED);
		}
		else {
			response.sendError(HttpServletResponse.SC_ACCEPTED);
		}
	}


	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " has started");
	}

	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has stopped");
	}
	
}
