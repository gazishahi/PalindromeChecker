# PalindromeChecker
A Palindrome checker Android app made with Java

There are 3 components to the app:
* Checker
* Game
* Entry list

## Checker
As the name suggests, this checks to see if the user input is a palindrome. The user can input anything and the app will simply return whether it is a palindrome or not.

## Game
From a predefined list of palindromes, the user has to guess a palindrome that is randomly selected from that list. The app will notify the player of how many attempts are left and if all attempts are exhausted, the next word is loaded. The user could also manually load the next word if they do not wish to guess the current one. If the player guesses correctly, the app will wait a few seconds before loading the next word. 

## Entry List
An entry list is created from the valid palindromes that are entered in the Checker. The user has the option to clear the entire list. The app is also capable of performing XML Serialization of the entry list, with the XML file being saved in the Documents folder.

## [Video Demonstration of App](https://youtu.be/iNexj4SIAqo)
