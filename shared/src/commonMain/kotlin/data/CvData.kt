package data

data class CvData(
    val firstName: String = "Yann",
    val lastName: String = "Van De Veire",
    val age: Int = 25,
    val subject: String = "Alternance",
    val profilDesc: String = "Actuellement en recherche d’une alternance pour ma troisième année de Bachelor informatique expert à Efficom Lille. ",
    val phone: String = "+33 6 51 53 85 85",
    val email: String = "yann.vdv.pro@gmail.com",
    val languages: Array<LanguageData> = arrayOf(
        LanguageData("Français",5),
        LanguageData("Anglais",4)
    ),
    val paragraph: Array<ParData> = arrayOf(
        ParData(
            title = "[Société Générale] [Auxiliaire de vacances]",
            date = "[2016]",
            description = "Emploi saisonnier : accueil (prise de chèques, remise de chéquiers/cartes bancaires, ...), prise de rendez-vous par téléphone"
        ),
        ParData(
            title = "[Collège Notre Dame] [Stage professeur de physique/chimie]",
            date = "[2015]",
            description = "Stage d’observation : techniques d’apprentissage avec le Professeur, intervention durant un cours"
        ),
        ParData(
            title = "[Forté Pharma] [Stage commercial en pharmacie]",
            date = "[2014]",
            description = "Stage d’observation : prise de rendez-vous par téléphone, découverte des techniques commerciales."
        )
    ),
    val paragraph2: Array<ParData> = arrayOf(
        ParData(
            title = "[Efficom lille] ",
            date = "[2019]-[….]",
            description = "- Première année : projet d’un site de domotique en groupe et d’un MarioBross en javascript.\n" +
                    "- Seconde année : projet drive producteur et projet d’aide au mémoire pour les étudiants."
        ),
        ParData(
            title = "[Université lille 1]",
            date = "[2017]-[2019]",
            description = "Licence SVTE, dans le but de devenir professeur d’SVT en collège ou lycée en réponse à ma passion pour la biologie et l’enseignement "
        ),
        ParData(
            title = "[Lycée St Dominique] ",
            date = "[2013]-[2017]",
            description = "BAC général spécialité SVT, en établissement privé, participation au comité d’organisation du bal de noël 2016\n"
        )
    )
)
