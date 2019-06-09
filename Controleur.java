package controleur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.Bulletin;
import modele.Connexion;
import modele.Personne;
import modele.UnBulletin;

public class Controleur {
        /**
         * Constructeur par défaut 
         */
	public Controleur() {
		
	}
	/**
         * Recherche un élève
         * @return une liste de type Persone 
         */
	public List<Personne> recherchePersonne(){
		ArrayList<Personne> personneList = new ArrayList<Personne>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT `personne`.`id`,`personne`.`nom`, `personne`.`prenom`,  `classe`.`nomclasse`, `niveau`.`nomniveau`\r\n" + 
					"FROM `personne`\r\n" + 
					"JOIN `inscription` \r\n" + 
					"ON `personne`.`id` = `inscription`.`personne_id` \r\n" + 
					"JOIN `classe` \r\n" + 
					"ON `inscription`.`classe_id` = `classe`.`id`\r\n" + 
					"JOIN `niveau`\r\n" + 
					"ON `classe`.`niveau_id` = `niveau`.`id`\r\n" + 
					"WHERE (( `personne`.`type` = \"eleve\"))");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				Personne personne = new Personne();
				personne.setId(rs.getInt("id"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomClasse(rs.getString("nomclasse"));
				personne.setNomNivDis(rs.getString("nomniveau"));

				personneList.add(personne);
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return personneList;
	}
	/**
         * Recherche un enseignant
         * @return  une liste de type Personne
         */
	public List<Personne> recherchePersonneEn(){
		ArrayList<Personne> personneList = new ArrayList<Personne>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT `personne`.`id`,`personne`.`nom`, `personne`.`prenom`,  `classe`.`nomclasse`, `discipline`.`nomdiscipline`\r\n" + 
					"FROM `personne`\r\n" + 
					"JOIN `enseignement` \r\n" + 
					"ON `personne`.`id` = `enseignement`.`personne_id` \r\n" + 
					"JOIN `classe` \r\n" + 
					"ON `enseignement`.`classe_id` = `classe`.`id`\r\n" + 
					"JOIN `discipline`\r\n" + 
					"ON `enseignement`.`discipline_id` = `discipline`.`id`\r\n" + 
					"WHERE (( `personne`.`type` = \"enseignant\"))");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				Personne personne = new Personne();
				personne.setId(rs.getInt("id"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomClasse(rs.getString("nomclasse"));
				personne.setNomNivDis(rs.getString("nomdiscipline"));

				personneList.add(personne);
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return personneList;
	}
	/**
         * Retourne la classe de l'élève
         * @return une ArrayList de type String
         */
	public ArrayList<String> obtenirNiveau(){
		ArrayList<String> nivListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT nomniveau FROM niveau");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				nivListe.add(rs.getString("nomniveau"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return nivListe;
	}
	/**
         * Retourne le numéro de la classe de l'élve
         * @param niveau 
         * @return 
         */
	public ArrayList<String> obtenirClasse(String niveau){
		ArrayList<String> classeListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT nomclasse FROM niveau JOIN classe on niveau.id = classe.Niveau_id where niveau.nomniveau =\""+niveau+"\"");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				classeListe.add(rs.getString("nomclasse"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return classeListe;
	}
	/**
         * Ajoute une personne
         * @param nom nom de l'élève
         * @param prenom prénom de l'élève
         * @param type checkbox élève ou enseignant
         * @return ID de la personne
         */
	public int ajouterPersonne(String nom, String prenom, String type) {
		Connection conn = Connexion.getConnexion();
		int idPersonne = -1;
		
		try
		{

			String requete = new String("INSERT INTO personne (nom, prenom, type) VALUES (?,?,?);");
			PreparedStatement statement = conn.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, nom);
			statement.setString(2, prenom);
			statement.setString(3, type);
			

			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				idPersonne = rs.getInt(1);
			}

		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		return idPersonne;
	}
	/**
         * Inscription d'un élève
         * @param idPersonne id de la personne avec la fonction ajouterPersonne
         * @param idClasse id de classe avec la fonction obtenir Idclass
         */
	public void ajoutInscription(int idPersonne, int idClasse) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("INSERT INTO inscription (classe_id, personne_id) VALUES (?,?);");
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.setInt(1, idClasse);
			statement.setInt(2, idPersonne);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}	
	}
	/**
         * Obtiens l'id de la classe 
         * @param classe
         * @return un int avec l'id de la classe
         */
	public int obtenirIdClasse(String classe){
		Connection conn = Connexion.getConnexion();
		int idClasse = -1;
		
		try
		{
			String requete = new String("SELECT id FROM Classe WHERE nomclasse=\""+classe+"\"");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				idClasse= rs.getInt("id");
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return idClasse;
	}
	/**
         * Retourne un tableau avec les différentes matières
         * @return une ArrayList avec les matières
         */
	public ArrayList<String> obtenirDispline(){
		ArrayList<String> disciplineListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT nomdiscipline FROM discipline");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				disciplineListe.add(rs.getString("nomdiscipline"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return disciplineListe;
	}
	/**
         * Retourne toute les classes dans un tableau 
         * @return retourne un ArrayLisst de String 
         */
	public ArrayList<String> obtenirToutClasse(){
		ArrayList<String> classeListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT nomclasse FROM classe");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				classeListe.add(rs.getString("nomclasse"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return classeListe;
	}
	/**
         * Obtiens l'ID de la discipline
         * @param discipline
         * @return un entier qui correspond à l'id
         */
	public int obtenirIdDiscipline(String discipline){
		Connection conn = Connexion.getConnexion();
		int idDis = -1;
		
		try
		{
			String requete = new String("SELECT id FROM discipline WHERE nomdiscipline=\""+discipline+"\"");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				idDis= rs.getInt("id");
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return idDis;
	}
	/**
         * Ajouter un enseignement
         * @param idDis id de la discipline 
         * @param idPersonne id de la personne 
         * @param idClasse id de la classe 
         */
	public void ajoutEnseignement(int idDis, int idPersonne, int idClasse) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("INSERT INTO enseignement (classe_id, discipline_id, personne_id) VALUES (?,?,?);");
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.setInt(1, idClasse);
			statement.setInt(2, idDis);
			statement.setInt(3, idPersonne);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}	
	}
	/**
         * Supprime un élève
         * @param id id de l'élève
         */
	public void supprimerEleve(String id) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("DELETE FROM inscription WHERE inscription.personne_id ="+id);
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
	}
	/**
         * Supprimer un enseignant
         * @param id id de l'enseignant
         */
	public void supprimerEnseignant(String id) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("DELETE FROM enseignement WHERE enseignement.personne_id ="+id);
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
	}
	/**
         * Obtiens le nom d'une personne 
         * @param id id de la personne 
         * @return un String avec le nom 
         */
	public String obtenirNomPersonne(String id){
		Connection conn = Connexion.getConnexion();
		String nom = null;
		
		try
		{
			String requete = new String("SELECT nom FROM personne WHERE id="+id);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				nom = rs.getString("nom");
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return nom;
	}
	/**
         * Obtiens le prénom d'une personne 
         * @param id id de la personne 
         * @return un String avec le prénom
         */
	public String obtenirPrenomPersonne(String id){
		Connection conn = Connexion.getConnexion();
		String prenom = null;
		
		try
		{
			String requete = new String("SELECT prenom FROM personne WHERE id="+id);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				prenom = rs.getString("prenom");
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return prenom;
	}
	/**
         * Modifie les attributs d'une personne 
         * @param nom
         * @param prenom
         * @param type
         * @param id 
         */
	public void modifierPersonne(String nom, String prenom, String type, String id) {
		Connection conn = Connexion.getConnexion();
		
		try
		{

			String requete = new String("UPDATE personne SET nom = ?, prenom = ?, type = ? WHERE id ="+id);
			PreparedStatement statement = conn.prepareStatement(requete);

			statement.setString(1, nom);
			statement.setString(2, prenom);
			statement.setString(3, type);
			

			statement.executeUpdate();

		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
	}
	/**
         * Modifie l'inscription d'une personne
         * @param idPersonne id personne 
         * @param idClasse id de classe
         */
	public void modifierInscription(String idPersonne, int idClasse) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("UPDATE inscription SET classe_id = ? where personne_id ="+idPersonne);
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.setInt(1, idClasse);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}	
	}
	/**
         * Modifie les attributs d'un enseignant
         * @param idDis id de sa discipline
         * @param idPersonne
         * @param idClasse 
         */
	public void modifierEnseignement(int idDis, String idPersonne, int idClasse) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("UPDATE enseignement SET classe_id = ?, discipline_id = ? WHERE personne_id="+idPersonne);
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.setInt(1, idClasse);
			statement.setInt(2, idDis);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}	
	}
	/**
         * Méthode de recherche du bulletin d'un élève 
         * @return un List de type Bulletin 
         */
	public List<Bulletin> rechercheBulletin(){
		ArrayList<Bulletin> bulletinList = new ArrayList<Bulletin>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT personne.nom, personne.prenom, bulletin.id, bulletin.appreciation FROM personne join inscription on personne.id = inscription.personne_id join bulletin on inscription.id = bulletin.inscription_id where personne.type=\"eleve\"");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				Bulletin bulletin = new Bulletin();
				bulletin.setId(rs.getInt("id"));
				bulletin.setNom(rs.getString("nom"));
				bulletin.setPrenom(rs.getString("prenom"));
				bulletin.setAppreciation(rs.getString("appreciation"));

				bulletinList.add(bulletin);
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return bulletinList;
	}
	/**
         * Retourne le Trimestre 
         * @param annee
         * @return une ArrayList de type String
         */
	public ArrayList<String> obtenirTrimestre(int annee){
		ArrayList<String> trimestreListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT numero FROM trismestre WHERE AnneeScolaire_id ="+annee);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				trimestreListe.add(rs.getString("numero"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return trimestreListe;
	}
	/**
         * Retourne l'année scolaire
         * @return une ArrayList de type String 
         */
	public ArrayList<String> obtenirAnnee(){
		ArrayList<String> anneeListe = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT AnneeScolaire_id FROM trismestre");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				anneeListe.add(rs.getString("AnneeScolaire_id"));
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return anneeListe;
	}
	/**
         * Recher un bulletin 
         * @param idBulletin id du bulletin 
         * @return une List de type UnBulletin
         */
	public List<UnBulletin> rechercheUnBulletin(int idBulletin){
		ArrayList<UnBulletin> bulletinList = new ArrayList<UnBulletin>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT note, appreciationeval, appreciationmat, nomdiscipline FROM evaluation JOIN detailbulletin ON evaluation.detailbulletin_id = detailbulletin.id join enseignement on enseignement.id = detailbulletin.enseignement_id join discipline on discipline.id = enseignement.discipline_id where detailbulletin.bulletin_id ="+idBulletin);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				UnBulletin unbulletin = new UnBulletin();
				unbulletin.setNote(rs.getInt("note"));
				unbulletin.setAppreciationEval(rs.getString("appreciationeval"));
				unbulletin.setAppreciationMatiere(rs.getString("appreciationmat"));
				unbulletin.setMatiere(rs.getString("nomdiscipline"));

				bulletinList.add(unbulletin);
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		
		return bulletinList;
	}
	/**
         * Retourne toutes les matières 
         * @return ArrayList de type String
         */
	public ArrayList<String> obtenirMatiere(){
		ArrayList<String> matiereList = new ArrayList<String>();
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT enseignement.id,nomdiscipline,nom FROM enseignement JOIN personne ON enseignement.personne_id = personne.id JOIN discipline ON discipline.id = enseignement.discipline_id");
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				String f = rs.getInt("id")+" - "+rs.getString("nomdiscipline")+" - "+rs.getString("nom");
				matiereList.add(f);
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		return matiereList;
	}
	/**
         * Vérifie l'existance et retourne l'id du bulletin
         * @param idBulletin id du bulletin 
         * @param idEnseignement id de l'enseignant
         * @return id du bulletin si existe , -1 sinon
         */
	public int verifierExistanceBulletinMat(int idBulletin, String idEnseignement) {
		int estPresent = -1;
		
		Connection conn = Connexion.getConnexion();
		
		try
		{
			String requete = new String("SELECT id from detailbulletin where detailbulletin.bulletin_id ="+idBulletin+" and detailbulletin.enseignement_id ="+idEnseignement);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(requete);

			while ( rs.next() )
			{
				estPresent = rs.getInt("id");
			}
		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		
		return estPresent;
	}
	/**
         * Ajouter une interrogation 
         * @param idbulletinMat id du bulletin 
         * @param note note de l'évaluation 
         * @param appre appréciation 
         */
	public void ajoutEval(int idbulletinMat, int note, String appre) {
		Connection conn = Connexion.getConnexion();
		
		try {
			String requete = new String("INSERT INTO evaluation (detailbulletin_id, note, appreciationeval) VALUES (?,?,?);");
			PreparedStatement statement = conn.prepareStatement(requete);
			
			statement.setInt(1, idbulletinMat);
			statement.setInt(2, note);
			statement.setString(3, appre);
			
			statement.executeUpdate();
			
		}catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}	
	}
	/**
         * Ajoute une matière dans un bulletin 
         * @param idBulletin
         * @param idEnseignement
         * @return l'id de la matière créée
         */
	public int ajoutBulletinMat(int idBulletin, int idEnseignement) {
		Connection conn = Connexion.getConnexion();
		int resultat = -1;

		try
		{

			String requete = new String("INSERT INTO detailbulletin (bulletin_id, enseignement_id) VALUES (?,?);");
			PreparedStatement statement = conn.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, idBulletin);
			statement.setInt(2, idEnseignement);
			

			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				resultat = rs.getInt(1);
			}
			else 
				resultat = -1;

		}
		catch (SQLException ex3)
		{
			while (ex3 != null)
			{
				System.out.println(ex3.getSQLState());
				System.out.println(ex3.getMessage());
				System.out.println(ex3.getErrorCode());
				ex3=ex3.getNextException();
			}
		}
		return resultat;
	
	}
	
	
	
}
