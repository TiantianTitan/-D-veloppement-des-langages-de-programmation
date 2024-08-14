# ILP

Langage support du cours Développement d'un langage de programmation (DLP), M1, Master STL, Sorbonne Université.

Ce dépôt contient un interprète en Java et un compilateur en Java vers C pour un langage simple.
Le langage est non-typé, similaire à Python, mais avec une syntaxe inspirée de ML.
Le langage est composé de quatre niveaux, chacun se basant sur le précédant pour ajouter des fonctionnalités plus avancées :
1. ILP1 : expressions, primitives, blocs syntaxiques variables locales
2. ILP2 : boucles, affectations, fonctions
3. ILP3 : exceptions, fonctions de première classe (lambda-expressions)
4. ILP4 : système objet à base de classes


### Contenu du dépot

- `Java/src/` : les sources Java de l'interprète et du compilateur ILP.
- `C/ilp.[ch]` : la bibliothèque d'exécution en C, utilisée pour exécuter le code ILP compilé en C.
- `ANTLRGrammars/` : les grammaires ANTLR 4 du langage ILP.
- `target/generated-sources/` : le résultat de la compilation des grammaires ANTLR 4 vers Java.
- `SamplesILP[1-4]/` : des programmes de test pour les différents niveaux ILP. Les répertoires comportent : des sources ILP (`.ilpml`), la valeur retournée attendue (`.result`) et l'affichage attendu (`.print`) de l'exécution de chaque source.
- `compile_ANTLR.sh` : script pour convertir les grammaires ANTLR (`.g4`) en Java, si le plug-in ANTLR 4 IDE pour Eclipse n'est pas utilisé.
- `C/install_gc.sh` : script pour compiler Boehm GC (contenu dans `C/gc-8.2.2.tgz`), s'il n'est pas disponible sur le système.
- `C/compileThenRun.sh` : script utilisé lors des tests du compilateur pour compiler le code C généré par la compilation, le lier à la bibliothèque d'exécution et lancer l'exécution du code exécutable obtenu.
- `.gitlab-ci.yml` : script d'intégration continue, qui lance automatiquement les tests unitaires à chaque _push_ sur le serveur GitLab.


### TME

Vous trouverez sur le site Moodle du cours un document Environnement des TME (TME 0) décrivant les instructions détaillées pour l'installation et le rendu des TME, ainsi que les énoncés de TME.

Rappelons que vous devez :
- faire un _fork_ de ce projet sur GitLab comme projet privé (bouton Forks en haut à droite) ;
- ajouter votre binôme et votre chargé de TME comme membre du projet privé, avec pour rôle _Maintainer_ ;
- faire un _import_ du projet privé dans Eclipse ;
- travailler en local en ajoutant les sources Java, C, ANTLR 4 demandées, ainsi que des jeux de test ;
- faire des _push_ réguliers sur le serveur GitLab ;
- modifier, si besoin, le fichier `.gitlab-ci.yml` pour inclure vos tests dans l'intégration continue ;
- vérifier le code visible dans votre projet GitLab et les résultats des tests d'intégration continue sur le serveur GitLab (le chargé de TME n'aura accès qu'au code visible sous GitLab, qui sera donc la version notée) ;
- effectuer vos rendus de TME en créant une _release_ avec pour nom de _tag_ : _rendu-tme-X_ pour indiquer que vous avez fini le TME.




### Liens utiles

- Site Moodle du cours : <https://moodle-sciences-23.sorbonne-universite.fr/course/view.php?id=631>
- Documentation de la PPTI (dont Java) : <https://www-ppti.ufr-info-p6.jussieu.fr>
- Documentation de git : <https://git-scm.com/book/en/v2>
- Documentation du plug-in EGit : <http://www.eclipse.org/egit/documentation>
- JDK Java : <https://jdk.java.net/>
- JUnit 4 : <https://junit.org/junit4/>
- Eclipse IDE : <https://www.eclipse.org/downloads/>
- ANTLR : <https://www.antlr.org/>


### Remerciements

Ce support est basé sur le code ILP9 du cours de ILP Christian Queinnec.
Le code original est disponible sur <https://github.com/paracamplus/ilp9>.
