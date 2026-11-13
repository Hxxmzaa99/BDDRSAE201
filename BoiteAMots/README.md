# Boîte à Mots — appli d'aide (Android)

Tu tapes les lettres de la grille (4×4 ou 5×5) et l'appli affiche **tous les mots français
possibles**, du plus long au plus court. On relie des lettres voisines dans les 8 directions,
sans repasser deux fois par la même case dans un mot. Touche un mot pour voir son chemin sur la
grille : les cases sont numérotées et reliées par un trait.

## Installer l'APK

1. Ouvre le dossier `BoiteAMots/` dans **Android Studio** (il télécharge Gradle et le SDK tout seul).
2. **Build ▸ Build App Bundle(s) / APK(s) ▸ Build APK(s)**.
   L'APK est créé dans `app/build/outputs/apk/debug/app-debug.apk`.
3. Copie-le sur ton téléphone et ouvre-le (autorise l'installation « sources inconnues »).
   Autre méthode : branche le téléphone en USB (débogage USB activé) et clique sur ▶ Run.

En ligne de commande, avec le SDK installé : `./gradlew assembleDebug`
(ou `assembleRelease` pour un APK optimisé, signé avec la clé debug).

## Architecture

```
BoiteAMots/
├── core/   Module Kotlin pur (sans Android) : la logique du jeu, testée unitairement
│   ├── Grid.kt        grille N×N + voisins précalculés (8 directions)
│   ├── Words.kt       normalisation : majuscules, sans accents (é→E, œ→OE)
│   ├── Trie.kt        arbre préfixe A-Z
│   ├── Solver.kt      recherche des mots (voir l'algorithme ci-dessous)
│   └── RandomGrid.kt  grille aléatoire pondérée (entraînement)
└── app/    Appli Android (Kotlin + Jetpack Compose, Material 3)
    ├── assets/mots.txt.gz          dictionnaire : 318 800 mots, 740 Ko
    ├── data/DictionaryRepository   charge le dictionnaire une fois, hors du thread UI
    ├── ui/SolverViewModel          état de l'écran (StateFlow), lance la résolution
    ├── ui/SolverScreen             saisie, réglages, liste des résultats
    └── ui/LetterGrid               dessin de la grille et du chemin du mot choisi
```

Le flux est à sens unique (MVVM) : l'écran envoie les actions au ViewModel, qui appelle le
`Solver` sur `Dispatchers.Default` et publie un nouvel état que l'écran affiche.

### Algorithme (≈ 15 ms pour une grille 5×5)

1. **Filtrage** : on garde seulement les mots dont les lettres sont disponibles dans la grille,
   en nombre suffisant. Il reste quelques milliers de mots sur 318 800.
2. **Trie** : ces mots candidats forment un petit arbre préfixe, donc peu de mémoire.
3. **Parcours en profondeur** depuis chaque case vers les voisins non visités. Le parcours
   s'arrête dès que le préfixe courant n'existe dans aucun mot.

La résolution se relance automatiquement dès que la grille est complète ou que la longueur
minimale change.

## Tests

`./gradlew :core:test` lance les tests de la logique : adjacence, pas de réutilisation d'une
case, validité du chemin, longueur minimale, accents.

## Dictionnaire

Le dictionnaire vient de [an-array-of-french-words](https://www.npmjs.com/package/an-array-of-french-words)
(licence MIT). Il a été normalisé en A-Z sans accents, sans les mots composés ni les noms propres,
avec 3 lettres minimum. Pour utiliser ta propre liste, remplace `app/src/main/assets/mots.txt.gz`
par un fichier gzip qui contient un mot par ligne en majuscules sans accents.
