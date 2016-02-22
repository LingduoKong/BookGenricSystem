# How to run it:
- 0, 	It is compiled and tesded by JDK 1.8
- 1, 	come into Bookhub folder
- 2, 	java ScoreCalculator <book JSON file path> <genre CSV file path>

Timer:
It costs me half hour to think about different solitions and corner cases.
About half hour to search libs for stemming and parsing JSON.
About three hours in total to write codes.
Another half hour to write readme file and some comments.  
In total: 4.5h

## Task list:
1,	read in a file with list of books
2,	read in a CSV file with genre/key/value
3,	find keys in a description and calculate their occurrence. 
4,	calculate the score of each genre

## Solutions:
1, 	use regular expression to match each key word. 
	(Straight forward but slow)
2, 	when reading in the book list, use inverted index to catch the position of each word, 
	then read CSV file and calculate the scores of each book.
	(Cost more space and write complex code, but fast)
3, 	Read in CSV file at first and build prefix tree for each key.
	Then read in boo list and match each read-in word with prefix tree. 
	If we find it, it will be added to calculate the final scores.
	(Cost less space than 2nd solution and faster than 1st solution)

## Corner case:
1, 	the key might be the combination of several words.
2, 	we need stemming before processing and matching.

## Final:
I choose 2nd soluction because the inverted index costs no more than 10M for 10k books, which is acceptable. (I assume that average length of a word is 10)
The index can be stored at JSON file and reloaded next time instead of recalculating.
With the increaing of books, it should be better to maintain because it is more frequent for genre to change.
A book's description should not be changed frequently.


