package net.javatutorial.tutorials;

import java.io.*;
import java.lang.Exception;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.javatutorial.tutorials.model.IdentityCard;
import net.javatutorial.tutorials.model.League;
import net.javatutorial.tutorials.model.Club;
import net.javatutorial.tutorials.model.Passport;
import net.javatutorial.tutorials.model.Player;


public class db {

    public static EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public db() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
        entityManager = entityManagerFactory.createEntityManager();
    }



    public ZonedDateTime DateInsert(int day, int m, int y){
        ZonedDateTime date = ZonedDateTime.of( y , m , day, 0, 0, 0, 0, ZoneId.systemDefault());
        return date;
    }

    public void query1(){
        //lista zawodników
        System.out.println("================================================");
        List<Player> list = entityManager.createQuery("SELECT p FROM Player p").getResultList();
        System.out.println("SELECT p FROM Player p");
        for (Player tmp : list) {
            System.out.println("\n");
            System.out.println("Player Details : \n" + tmp.toString());
            System.out.println("\n");
        }
        System.out.println("================================================");
    }

    public void query2(){
        //lista klubów
        System.out.println("================================================");
        List<Club> list = entityManager.createQuery("SELECT p FROM Club p").getResultList();
        System.out.println("SELECT p FROM Club p");
        for (Club tmp : list) {
            System.out.println("\n");
            System.out.println("Club Details : \n" + tmp.toString());
            System.out.println("\n");
        }
        System.out.println("================================================");
    }

    public void query3(int pageNumber, int pageSize){
        //stronnicowanie lista lig
        System.out.println("================================================");
        Query query = entityManager.createQuery("SELECT p FROM League p");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        List <League> list = query.getResultList();
        System.out.println("SELECT p FROM League p");
        System.out.println("\nPage number " + pageNumber);
        for (League tmp : list) {
            System.out.println("League Details : \n" + tmp.toString());
            System.out.println("\n");
        }
        System.out.println("================================================");
    }

    public void query4(){
        //szukaj zawodników o imieniu Andrzej
        TypedQuery<Player> query = entityManager.createQuery(
                "SELECT c FROM Player c WHERE c.firstName LIKE :name", Player.class);
        List<Player> list =  query.setParameter("name", "Andrzej").getResultList();
        System.out.println("SELECT c FROM Player c WHERE c.firstName LIKE :Andrzej");
        for (Player tmp : list) {
            System.out.println("\n");
            System.out.println("Andrzej Details : \n" + tmp.toString());
            System.out.println("\n");
        }
        System.out.println("================================================");
    }

    public void query5(){
        //ile jest klubów w bazie
        Long x = (Long)entityManager.createQuery("SELECT count(p) FROM Club p").getSingleResult();
        System.out.println("SELECT count(p) FROM Club p");
        System.out.println("W bazie jest " + x + " klubow.");
        System.out.println("================================================");
    }

    public void query6(){
        //lista lig
        System.out.println("================================================");
        List<Passport> list = entityManager.createQuery("SELECT p FROM Passport p").getResultList();
        System.out.println("SELECT p FROM Passport p");
        for (Passport tmp : list) {
            System.out.println("\n");
            System.out.println("Passport Details : \n" + tmp.toString());
            System.out.println("\n");
        }
        System.out.println("================================================");
    }

    public static List<Player> getAllPlayers(){
        return entityManager.createQuery("SELECT p FROM Player p").getResultList();
    }

    public static List<Club> getAllClubs(){
        return entityManager.createQuery("SELECT p FROM Club p").getResultList();
    }

    public static List<IdentityCard> getAllIdentityCards(){
        return entityManager.createQuery("SELECT p FROM IdentityCard p").getResultList();
    }

    public static List<Passport> getAllPassports(){
        return entityManager.createQuery("SELECT p FROM Passport p").getResultList();
    }

    public static List<League> getAllLeagues(){
        return entityManager.createQuery("SELECT p FROM League p").getResultList();
    }

    public static Object findByID(Class<?> cl, int id){
        return entityManager.find(cl, id);
    }

    public static Object findByID(Class<?> cl, String id){
        return entityManager.find(cl, id);
    }

    public Player PlayerCreate(Player p, String imie, String nazwisko, Passport pass, IdentityCard idc){
        p.setFirstName(imie);
        p.setLastName(nazwisko);
        p.setPassportDetails(pass);
        p.setIdentityCardDetails(idc);
        return p;
    }

    public Passport PassportCreate(Passport p, String x, String q, ZonedDateTime d){
        p.setPassportNumber(x);
        p.setDataWydania(d);
        p.setKodPanstwa(q);
        return p;
    }

    public IdentityCard ICCreate(IdentityCard p, String x, ZonedDateTime d){
        p.setserialNumber(x);
        p.setDataWydania(d);
        return p;
    }

    public League LeagueCreate(League l, String name, String country){
        l.setLeagueName(name);
        l.setCountry(country);
        return l;
    }

    public Club ClubCreate(Club c, String name, League l){
        c.setTeamName(name);
        c.setLeagueDes(l);
        return c;
    }

    public Player AddToTeam(Player p, Club c){
        p.setClubDes(c);
        return p;
    }

    public String insert(Passport passport) throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(passport);
        entityManager.getTransaction().commit();
        if(entityManager.find(Passport.class, passport.getPassportNumber()) == null)
            throw new Exception("Error passport insert");
        return passport.getPassportNumber();
    }

    public String insert(IdentityCard identitycard) throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(identitycard);
        entityManager.getTransaction().commit();
        if(entityManager.find(IdentityCard.class, identitycard.getserialNumber()) == null)
            throw new Exception("Error identitycard insert");
        return identitycard.getserialNumber();
    }

    public int insert(Club club) throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(club);
        entityManager.getTransaction().commit();
        if(entityManager.find(Club.class, club.getClubid()) == null)
            throw new Exception("Error club insert");
        return club.getClubid();
    }

    public int insert(Player player) throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(player);
        entityManager.getTransaction().commit();
        if(entityManager.find(Player.class, player.getPlayerId()) == null)
            throw new Exception("Error player insert");
        return player.getPlayerId();
    }

    public int insert(League league) throws Exception{
        entityManager.getTransaction().begin();
        entityManager.persist(league);
        entityManager.getTransaction().commit();
        if(entityManager.find(League.class, league.getLeagueid()) == null)
            throw new Exception("Error league insert");
        return league.getLeagueid();
    }

    public static void merge(Object o){
        entityManager.getTransaction().begin();
        entityManager.persist(o);
        entityManager.getTransaction().commit();
    }

    public static void remove(Object o){
        entityManager.getTransaction().begin();
        entityManager.remove(o);
        entityManager.getTransaction().commit();
    }

    public static void truncate(String o){
        entityManager.getTransaction().begin();
        if(o.toLowerCase().equals("player")){
            entityManager.createNativeQuery("truncate table Player").executeUpdate();
        }
        else if(o.toLowerCase().equals("club")){
            entityManager.createNativeQuery("truncate table Club").executeUpdate();
        }
        else if(o.toLowerCase().equals("passport")){
            entityManager.createNativeQuery("truncate table Passport").executeUpdate();
        }
        else if(o.toLowerCase().equals("identitycard")){
            entityManager.createNativeQuery("truncate table IdentityCard").executeUpdate();
        }
        else if(o.toLowerCase().equals("league")){
            entityManager.createNativeQuery("truncate table League").executeUpdate();
        }
        entityManager.getTransaction().commit();
    }

    public void close(){
        entityManager.clear();
        entityManager.close();
        entityManagerFactory.close();
    }

}