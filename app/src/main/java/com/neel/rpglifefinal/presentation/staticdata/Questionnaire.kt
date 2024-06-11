package com.neel.rpglifefinal.presentation.staticdata

data class Questionnaire(val type : Stats,val question : String,val option1 : String,
                         val option2 : String,val option3: String,
                         val score1 : Int, val score2 : Int, val score3 : Int)
enum class Stats {
    STR,INT,WIS,CON,CHR,DEX
}

val questionList = listOf(


            Questionnaire(Stats.STR,"How often do you Exercise?","I go to gym regularly","Sometimes, when I feel like doing it.","I almost never exercise",3,2,0),
Questionnaire(Stats.STR,"Can you to lift your own body weight easily?","Very easily and some more","Just barely","I can hardly lift any weights",3,2,1),
Questionnaire(Stats.STR,"The lift is not working, there are big sacks of rice you need to take upstairs, you...  ","Carry the sacks upstairs","ask for assistance","wait foe the lift to start working again",3,1,0),
Questionnaire(Stats.STR,"What is the least no of push ups you can do (properly)?","less than 5","between 5 to 15","more than 15",1,2,3),
Questionnaire(Stats.STR,"How high can you trow a stone?","Over a building","a few feet","just over another person",3,2,1),

Questionnaire(Stats.CON,"How often do you get sick?","Quite often...","Rarely, maybe one or two times a month","Once in a blue moon",3,2,1),
Questionnaire(Stats.CON,"Can you jog for a mile continuously without a break?","Yes","No","Maybe",3,1,2),
Questionnaire(Stats.CON,"Do you have any allergies?","Yes, many...","No","Some allergies...",1,3,2),
Questionnaire(Stats.CON,"can you indulge in street food without any consequences(i.e. stomach ache, food poisoning, etc.)?","Yes","No","Sometimes",3,1,2),
Questionnaire(Stats.CON,"In a test of physical endurance how long can you last?","Wreck the test","Fail the test","Finish but with ample rest",3,1,2),

Questionnaire(Stats.DEX,"Can you catch a ball coming at you suddenly?","Yes","No","Maybe",3,1,2),
Questionnaire(Stats.DEX,"Do you do any of the following activities?:Archery, Video games, Acrobatics, Gymnastics, Martial Arts","Yes","No", "Sometimes",3,1,2),
Questionnaire(Stats.DEX,"Can you drive a car or any motor vehicle?","Yes","No","Yes, but only one of the type", 3,1,2),
Questionnaire(Stats.DEX,"How often do you break things due to your clumsiness?","Often","Never","Sometimes",1,3,2),
Questionnaire(Stats.DEX,"Can you pickup dance moves once taught?","Yes at ease on first try...","I'm inept at dancing","After several trial...",3,1,2),

Questionnaire(Stats.INT,"What describes you best...","Honor student","Average Joe","Barely passing",3,2,1),
Questionnaire(Stats.INT,"Are you proficient in more than two languages?","Yes","No","Yes but little", 3,1,2),
Questionnaire(Stats.INT,"Can you deduce the plot of a movie or a book hallway through the source?","Yes","No","Sometimes",3,1,2),
Questionnaire(Stats.INT,"Do you often get confused by rules and instruction?","Never","All the time","Often",3,1,2),
Questionnaire(Stats.INT,"Can you play puzzle games like chess, bridge, or sudoku easily?","Yes","No","Maybe",3,1,2),

Questionnaire(Stats.WIS,"How often do you plan your day ahead?","Everyday","During trips and events","Never",3,2,1),
Questionnaire(Stats.WIS,"How often are you tight on your money by the end of the month?","Almost never","Often struggle to keep track of my expenditures","Happens often when nearing an event",3,1,2),
Questionnaire(Stats.WIS,"Do you own up to your won mistakes?","Yes","No","sometimes",3,1,2),
Questionnaire(Stats.WIS,"Can you settle disputes between two parties peacefully and unbiased?","Yes","No","Maybe",3,1,2),
Questionnaire(Stats.WIS,"Do people come to you for advice?","Yes","No","Sometimes",3,1,2),

Questionnaire(Stats.CHR,"Do you get hit on by the member of opposite sex/same sex often?","Yes","No","Sometimes",3,1,2),
Questionnaire(Stats.CHR,"Can you make people do your bidding often?","All the time","No one does anything for mr ever","Sometimes",3,1,2),
Questionnaire(Stats.CHR,"Do people get angry at you often?","Yes","No","sometimes",3,1,2),
Questionnaire(Stats.CHR,"Do people often confide in you?","Yes","No","Sometimes",3,1,2),
Questionnaire(Stats.CHR,"Are you considered handsome by beauty standard other than your grandma?","Yes","No","Maybe",3,1,2),
)