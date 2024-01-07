# Application Intérima
**Authors** :
  - [Arthur Sapin](https://github.com/a-sapin)
  - [Lorenzo Puccio](https://gitlab.com/StOil-L)
  - [Océane Ongaro](https://github.com/AlyssaShep)

## Résumé
Cette application développée sous Android Studio propose une interface pour différents types d'utilisateurs (principalement chercheurs d'emploi et employeurs) avec une base de données distante.

La base de données distante était hébergée sur un nano-ordinateur *Raspberry Pi 4*, mais l'hébergement de la base a été suspendu quelques semaines après la fin du projet académique. La partie réseau de l'application n'est donc plus fonctionnelle puisque la base distante n'est plus accessible.

## Abstract

This project was carried out for academia at the University of Montpellier, during my first wear as a Software Engineering Masters Degree student.

It was developed as part of an assignment for [hai811i](https://mcc.umontpellier.fr/?q=node/63768).
Interima is an Android app built for the browsing, application and management of temping offers. Its backend is a MySQL image deployed on a Raspberry Pi 4 with Docker.

## Features

| **Features**                                                       | **No user role** | **Job seeker** | **Employer** | **Temping agency** |
|--------------------------------------------------------------------|------------------|----------------|--------------|--------------------|
| Browse relevant temping offers based on device's location          |         Y        |        Y       |       Y      |          Y         |
| Sign up and log in as specific user role                           |         Y        |        Y       |       Y      |          Y         |
| Check full offer details                                           |         Y        |        Y       |       Y      |          Y         |
| Bookmark offer                                                     |         N        |        Y       |       N      |          N         |
| Apply from scratch or reusing a previous application's information |         N        |        Y       |       Y      |          Y         |
| Check application details and status                               |         N        |        Y       |       Y      |          Y         |
| Manage application status                                          |         N        |        N       |       Y      |          Y         |
| Check search history                                               |         N        |        Y       |       Y      |          Y         |
| Check submitted resumes and cover letters                          |         N        |        Y       |       N      |          N         |
| Publish offer from in-app form                                     |         N        |        N       |       Y      |          Y         |
| Publish offer from JSON file                                       |         N        |        N       |       N      |          Y         |
| Check published offers                                             |         N        |        N       |       Y      |          Y         |

A showcase video of these features can be found on [Océane ONGARO's GitHub.io page](https://oceaneongaro.github.io/post/interima/montage.mp4).

## Getting Started

As the aforementioned MySQL image has been deleted off the Raspberry Pi 4, the app itself has been decommissionned.

However, if you still wish to run what remains of the app's frontend, you can do so by importing this project in Android Studio and running it either on actual hardware or with an Android Virtual Device - in which case we recommend a Google Pixel 5 with an Android API of version 28+ for best effect.
