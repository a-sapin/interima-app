-- Utilisateurs
INSERT INTO utilisateur (mdp, role) values ('dummypwd1', 'ChercheurEmploi');
INSERT INTO utilisateur (mdp, role) values ('dummypwd2', 'Employeur');
INSERT INTO utilisateur (mdp, role) values ('dummypwd3', 'AgenceInterim');
INSERT INTO utilisateur (mdp, role) values ('dummypwd4', 'Gestionnaire');
INSERT INTO utilisateur (mdp, role) values ('dummypwd5', 'Gestionnaire');
INSERT INTO utilisateur (mdp, role) values ('dummypwd6', 'Gestionnaire');

-- Chercheurs d'emploi
INSERT INTO chercheuremploi (idUti, nom, prenom, email, nationalite, tel, ville) values ('1', 'Ducat', 'Jean', 'jean.ducat@gmail.com', 'Français', '0431295764', 'Nîmes');

-- Employeurs
INSERT INTO employeur (idUti, nomEntreprise, nomServDept, nomSousSD, siret, nomC1, nomC2, emailC1, emailC2, telC1, telC2, adresse) values ('2', 'ClapGemini', 'Solutions bancaires', 'Equipe YEN', '43793330200022', 'Merrière', 'Blanco', 'jacqueline.merriere@gmail.com', 'walter.blanco@gmail.com', '0793132009', '069864512037', '25 avenue des boulevards, Montpellier 34090');

-- Agences d'intérim
INSERT INTO agenceinterim (idUti, nomAgence, siret, nomC1, nomC2, emailC1, emailC2, telC1, telC2, adresse) values ('3', 'Taggart Intérim', '48213330200057', 'Warrens', 'Romero', 'flynn.warrens@gmail.com', 'john.romero@gmail.com', '067941320685', '066623080975', '32 boulevard des avenues, Montpellier 34090');

-- Gestionnaires
INSERT INTO gestionnaire (idUti, email) values ('4', 'oceane.ongaro@etu.umontpellier.fr');
INSERT INTO gestionnaire (idUti, email) values ('5', 'lorenzo.puccio@etu.umontpellier.fr');
INSERT INTO gestionnaire (idUti, email) values ('6', 'arthur.sapin@etu.umontpellier.fr');

-- Offres
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('2', 'Développeur fullstack', '2023-05-22', '2023-06-02', '2023-07-01', '2024-03-31', 'https://www.clapgemini.com/fr-fr/jobs/ie27I4gBiNZTbKC6tY6z/', '77.5', '43.61962374707733', '3.8496374146829155', 'https://companieslogo.com/img/orig/CAP.PA-9b4110b0.png', 'Placeholder description 1');
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('2', 'Développeur backend PHP', '2023-05-30', '2023-06-15', '2023-07-01', '2024-03-31', 'https://www.clapgemini.com/fr-fr/jobs/dz925d6zdfTF2cCEZE7k/', '57.5', '43.61962374707733', '3.8496374146829155', 'https://companieslogo.com/img/orig/CAP.PA-9b4110b0.png', 'Placeholder description 2');
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('2', 'Architecte logiciel', '2023-06-15', '2023-08-01', '2023-09-01', '2023-12-31', 'https://www.clapgemini.com/fr-fr/jobs/e95d5jyu6XZ5S2VR56a4/', '100.0', '43.61962374707733', '3.8496374146829155', 'https://companieslogo.com/img/orig/CAP.PA-9b4110b0.png', 'Placeholder description 3');
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('3', 'Développeur Java', '2023-05-31', '2023-06-30', '2023-09-01', '2024-01-31', 'https://www.clapgemini.com/fr-fr/jobs/ecg955zx652vb5er6az5/', '60.0', '43.63400260312132', '3.8437472846142517', 'https://cdn-icons-png.flaticon.com/512/226/226777.png', 'Placeholder description 4');
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('3', 'Testeur Java', '2023-07-01', '2023-08-01', '2023-10-01', '2024-05-31', 'https://www.clapgemini.com/fr-fr/jobs/85dez049xz5ezECC22v8/', '82.5', '43.605536666979305', '3.8935290839358863', 'https://cdn-icons-png.flaticon.com/512/226/226777.png', 'Placeholder description 5');
INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img) values ('3', 'Ingénieur Cloud', '2023-04-22', '2023-05-15', '2023-06-15', '2023-08-31', 'https://www.clapgemini.com/fr-fr/jobs/ie27I4gBiNZTbKC6tY6z/', '70.0', '43.60890589149053', '3.907832384028275', 'https://fr.seaicons.com/wp-content/uploads/2015/06/cloud-icon2.png', 'Placeholder description 6');

-- Candidatures
INSERT INTO candidature (idUti, lienCV, lienLM, commentaires) values ('1', 'dummy', 'dummy', 'Cette offre est parfaite pour moi!', 'En attente');

-- Candidatures d'offre
INSERT INTO candidatureoffre ('1', '3');

-- Candidatures spontanée

-- Recherches

-- Mots clef
INSERT INTO motclef (mot) values ('developpeur');
INSERT INTO motclef (mot) values ('fullstack');
INSERT INTO motclef (mot) values ('backend');
INSERT INTO motclef (mot) values ('php');
INSERT INTO motclef (mot) values ('architecte');
INSERT INTO motclef (mot) values ('logiciel');
INSERT INTO motclef (mot) values ('java');
INSERT INTO motclef (mot) values ('testeur');
INSERT INTO motclef (mot) values ('ingenieur');
INSERT INTO motclef (mot) values ('cloud');

-- Les offres contiennent les mots clés...
INSERT INTO contient (idOffre, idMot) values ('1', '1');
INSERT INTO contient (idOffre, idMot) values ('1', '2');
INSERT INTO contient (idOffre, idMot) values ('2', '1');
INSERT INTO contient (idOffre, idMot) values ('2', '3');
INSERT INTO contient (idOffre, idMot) values ('2', '4');
INSERT INTO contient (idOffre, idMot) values ('3', '5');
INSERT INTO contient (idOffre, idMot) values ('3', '6');
INSERT INTO contient (idOffre, idMot) values ('4', '1');
INSERT INTO contient (idOffre, idMot) values ('4', '7');
INSERT INTO contient (idOffre, idMot) values ('5', '8');
INSERT INTO contient (idOffre, idMot) values ('5', '7');
INSERT INTO contient (idOffre, idMot) values ('6', '9');
INSERT INTO contient (idOffre, idMot) values ('6', '10');

-- Favoris

-- Abonnements
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Ponctuel', '4', 'Permission de publier une annonce', 'Sans conditions');
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Mois', '20', 'Publications illimitées pendant un mois', 'Résiliation avec préavis de 7 jours');
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Trimestre', '40', 'Publications illimitées pendant 3 mois', 'Résiliation avec préavis de 14 jours');
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Semestre', '60', 'Publications illimitées pendant 6 mois', 'Résiliation avec préavis de 30 jours');
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Année', '90', 'Publications illimitées pendant un an', 'Résiliation avec préavis de 2 mois');
INSERT INTO abonnement (nom, prix, avantages, conditions) values ('Illimité', '120', 'Publications illimitées jusqu\'à résiliation', 'Résiliation avec préavis de 4 mois');

-- Souscription
INSERT INTO souscription (idAbonnement, idUti, souscription, expiration, paiement) values ('2', '2', '2023-05-01', '2023-06-01', 'Prélèvement automatique');
INSERT INTO souscription (idAbonnement, idUti, souscription, expiration, paiement) values ('3', '5', '2023-05-16', '2024-05-16', 'Carte');