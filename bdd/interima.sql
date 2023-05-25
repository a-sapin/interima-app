CREATE TABLE IF NOT EXISTS utilisateur (
  id int NOT NULL AUTO_INCREMENT,
  mdp varchar(255) NOT NULL,
  role varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS chercheuremploi (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  nom varchar(255) NOT NULL,
  prenom varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  nationalite varchar(255) NOT NULL,
  tel varchar(12) NOT NULL,
  ville varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY ChercheurEmploi_FK (idUti),
  CONSTRAINT ChercheurEmploi_FK FOREIGN KEY (idUti) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS employeur (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  nomEntreprise varchar(255) NOT NULL,
  nomServDept varchar(255) NOT NULL,
  nomSousSD varchar(255) NOT NULL,
  siret varchar(14) NOT NULL,
  nomC1 varchar(255) NOT NULL,
  nomC2 varchar(255) DEFAULT NULL,
  emailC1 varchar(255) NOT NULL,
  emailC2 varchar(255) DEFAULT NULL,
  telC1 varchar(12) NOT NULL,
  telC2 varchar(12) DEFAULT NULL,
  adresse varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY Employeur_FK (idUti),
  CONSTRAINT Employeur_FK FOREIGN KEY (idUti) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS agenceinterim (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  nomAgence varchar(255) NOT NULL,
  siret varchar(14) NOT NULL,
  nomC1 varchar(255) NOT NULL,
  nomC2 varchar(255) DEFAULT NULL,
  emailC1 varchar(255) NOT NULL,
  emailC2 varchar(255) DEFAULT NULL,
  telC1 varchar(12) NOT NULL,
  telC2 varchar(12) DEFAULT NULL,
  adresse varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY AgenceInterim_FK (idUti),
  CONSTRAINT AgenceInterim_FK FOREIGN KEY (idUti) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS gestionnaire (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY Gestionnaire_FK (idUti),
  CONSTRAINT Gestionnaire_FK FOREIGN KEY (idUti) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS offre (
  id int NOT NULL AUTO_INCREMENT,
  idEmp int NOT NULL,
  titre varchar(255) NOT NULL,
  publication date NOT NULL,
  fermeture date NOT NULL,
  debut date NOT NULL,
  fin date NOT NULL,
  url varchar(255) NOT NULL,
  salaire float NOT NULL,
  geolat double NOT NULL,
  geolong double NOT NULL,
  img varchar(255),
  description varchar(2047) NOT NULL,
  PRIMARY KEY (id),
  KEY OffreAgence_FK (idEmp),
  CONSTRAINT OffreAgence_FK FOREIGN KEY (idEmp) REFERENCES agenceinterim (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT OffreEmployeur_FK FOREIGN KEY (idEmp) REFERENCES employeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS candidature (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  lienCV varchar(255) NOT NULL,
  lienLM varchar(255) NOT NULL,
  commentaires varchar(2047),
  statut varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY Candidature_FK (idUti),
  CONSTRAINT Candidature_FK FOREIGN KEY (idUti) REFERENCES chercheuremploi (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS candidatureoffre (
  id int NOT NULL AUTO_INCREMENT,
  idOffre int NOT NULL,
  idCandidature int NOT NULL,
  PRIMARY KEY (id),
  KEY CandidatureOffreO_FK (idOffre),
  KEY CandidatureOffreC_FK (idCandidature),
  CONSTRAINT CandidatureOffreC_FK FOREIGN KEY (idCandidature) REFERENCES candidature (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CandidatureOffreO_FK FOREIGN KEY (idOffre) REFERENCES offre (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS candidaturespontanee (
  idEmp int NOT NULL,
  id int NOT NULL AUTO_INCREMENT,
  debut date NOT NULL,
  fin date NOT NULL,
  metier varchar(255) NOT NULL,
  idCandidature int DEFAULT NULL,
  PRIMARY KEY (id),
  KEY CandidatureSpontaneeAgence_FK (idEmp),
  KEY idCandidature (idCandidature),
  CONSTRAINT candidaturespontanee_ibfk_1 FOREIGN KEY (idCandidature) REFERENCES candidature (id),
  CONSTRAINT CandidatureSpontaneeAgence_FK FOREIGN KEY (idEmp) REFERENCES agenceinterim (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CandidatureSpontaneeEmployeur_FK FOREIGN KEY (idEmp) REFERENCES employeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS recherche (
  id int NOT NULL AUTO_INCREMENT,
  idUti int NOT NULL,
  metier varchar(255) NOT NULL,
  lieu varchar(255) NOT NULL,
  debut date NOT NULL,
  fin date NOT NULL,
  dateRecherche date NOT NULL,
  PRIMARY KEY (id),
  KEY Recherche_FK (idUti),
  CONSTRAINT Recherche_FK FOREIGN KEY (idUti) REFERENCES utilisateur (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS motclef (
  id int NOT NULL AUTO_INCREMENT,
  mot varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contient (
  id int NOT NULL AUTO_INCREMENT,
  idOffre int NOT NULL,
  idMot int NOT NULL,
  PRIMARY KEY (id),
  KEY ContientOffre_FK (idOffre),
  KEY ContientMot_FK (idMot),
  CONSTRAINT ContientMot_FK FOREIGN KEY (idMot) REFERENCES motclef (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT ContientOffre_FK FOREIGN KEY (idOffre) REFERENCES offre (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS favori (
  id int NOT NULL AUTO_INCREMENT,
  idOffre int NOT NULL,
  idUti int NOT NULL,
  PRIMARY KEY (id),
  KEY FavoriOffre_FK (idOffre),
  KEY FavoriChercheur_FK (idUti),
  CONSTRAINT FavoriChercheur_FK FOREIGN KEY (idUti) REFERENCES chercheuremploi (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FavoriOffre_FK FOREIGN KEY (idOffre) REFERENCES offre (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS abonnement (
  id int NOT NULL AUTO_INCREMENT,
  nom varchar(255) NOT NULL,
  prix float NOT NULL,
  avantages varchar(2047) NOT NULL,
  conditions varchar(2047) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS souscription (
  id int NOT NULL AUTO_INCREMENT,
  idAbonnement int NOT NULL,
  idUti int NOT NULL,
  souscription date NOT NULL,
  expiration date NOT NULL,
  paiement varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY SouscriptionAb_FK (idAbonnement),
  KEY SouscriptionAge_FK (idUti),
  CONSTRAINT SouscriptionAb_FK FOREIGN KEY (idAbonnement) REFERENCES abonnement (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT SouscriptionAge_FK FOREIGN KEY (idUti) REFERENCES agenceinterim (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT SouscriptionEmp_FK FOREIGN KEY (idUti) REFERENCES employeur (id) ON DELETE CASCADE ON UPDATE CASCADE
);