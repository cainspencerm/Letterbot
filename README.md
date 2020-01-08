# Letterbot
A Discord bot to search for films, lists, and contributors in Letterboxd (https://letterboxd.com/).

***Letterboxd: Track films you’ve watched. Save those you want to see. Tell your friends what’s good.***

Letterbot acts as an API search for films, lists, and contributors. A Discord user can ask the bot to find one of these three things. The bot responds with an LBML-formatted message containing any important information in which the user might be interested.

The bot works with two commands for the moment:
  1. -film
  2. -list
  
Following these commands, a user includes the title of the film or list. A partial title works, but might not produce the film or list a user wants. For instance, I can type:

  *-film ad astra*

The bot will then respond with an embedded film: Ad Astra (https://letterboxd.com/film/ad-astra-2019/). Likewise, I can type:

  *-list 1001 movies you must see*

The bot will respond with an embedded list: 1001 Movies You Must See Before You Die (https://letterboxd.com/peterstanley/list/1001-movies-you-must-see-before-you-die/).
