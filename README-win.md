# Utilisation d'ILP sous Windows

Le compilateur ILP peut fonctionner sous Windows avec Windows Subsystem for Linux 2, à condition de posséder une version de Windows 10 récente.

Étapes d'installation :
- Installer Eclipse IDE pour le développement Java : <https://www.eclipse.org/downloads/>
- Installer les greffons ANTLR4 IDE et egit depuis le Marketplace Eclipse.
- Installer un Java Development Kit et configurer Eclipse pour l'utiliser : <https://openjdk.java.net/>
- À ce niveau, seul l'interprète ILP fonctionnera, pas le compilateur.
- Activer Windows Subsystem for Linux 2 : <https://docs.microsoft.com/fr-fr/windows/wsl/install-win10>
- Installer depuis Microsoft Store la dernière version d'Ubuntu (20.04 TLS au moment où ce document est écrit).
- Installer la chaîne de compilation C dans Ubuntu en entrant dans un terminal Windows la commande suivante :
```
wsl sudo apt-get install build-essential
```
- Le compilateur ILP devrait maintenant fonctionner.

Attention, pour fonctionner, le script `C/compileThenRun.sh` doit être formaté avec des fins de ligne de type Unix (`\n`) et pas DOS / Windows (`\r\n`).
Par défaut, les fichiers textes sont transformés automatiquement en style DOS / Windows lors de l'import du projet avec git, ce qui pose problème.
Un fichier `.gitattributes` a été ajouté au projet pour éviter ce comportement.


### Sans Windows Subsystem for Linux

Une solution alternative, mais plus lourde que WSL, pour faire fonctionner le compilateur ILP sans WSL est d'installer un système Linux complet dans une machine virtuelle (e.g., VirtualBox).

Sans WSL ni machine virtuelle, ILP n'aura pas accès à la chaîne de compilation.
Par contre, l'interprète, écrit en Java pur, fonctionnera.
