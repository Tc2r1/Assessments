# HIGHEST GROSSING FILMS!
With the following list of movies (attached image), create an Activity that includes a RecyclerView that shows on each item the name of the movie, the Distributor and the Gross money: 
![Image of Films](http://i.imgur.com/xLQy4Kb.png)
Remember to use a ViewHolder and an Adapter.
 
Result: 
![Video Of App](http://i.imgur.com/vWy6CNk.gif)

**NOTES: **
I decided to use many different forms of data storage in order to display the information on the picture. I stored the data using SQLite locally, as well as storing the data remotely, Then I parsed through the data sources to create an array of film objects. I used a custom built adapter to display this list of film objects to the recyclerview on the second activity. 

I passed my ArrayList to the seperate activity by making the objects Parcelable.

For UI I used a simplistic view involving RecyclerView with alternating row colors. 
