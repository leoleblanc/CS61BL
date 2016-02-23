Ryan Kapur cs61bl-cj
Leo LeBlanc cs61bl-bx

Description of each test's purpose:
*The tests in DotTest are for basic dot-implementation functionality

Board Tests:
*testNumberSelected selects two dots and confirms that they were both selected correctly

*testIsClosedShape confirms that basic shapes such as squares/	donuts/5-dot shapes return true 

*testBoardConstructor verfies that the creation of a new board works with the correct number of dots

*testPresetBoard confirms that the colors on the preset are working correctly 

*testCanMakeMove confirms that this method returns the correct boolean

*testIsGameOver confirms that at game over, no dots can be selected and that the game is over when no more moves can be made

*testCanSelect confirms that when nothing is selected and when the game is not over, a dot can be selected (also checks a case where you cannot select a dot)

*testSelectDot confirms that any individual dot can be selected

*testCanDeselect confirms that you a dot that isn't selected cannot be deselected--and that it works only upon the current stack

*testRemoveSelectedDots confirms that all the selected dots are removed correctly and that the stack is reset in addition

*testDropRemainingDots confirms that the dots are being moved to where the previously selected/removed dots were

*testfillRemoveDots confirms that the dots being brought from the top are not null

*testgetScore confirms that the score in incremently correctly and starts at zero

*testFindBestSquare confirms that it finds the square that provides the most points by removing the square of a certain color of dots-- appearing the most.


Contributions:
*Ryan and Leo brainstormed collaboratively on how to go about creating the board and various functions  
*Leo lead the function writing for part one, debugging
*Ryan lead brainstorming how to solve the remove methods, debugging
*For part two, these roles switched and was more collaborative
*For part three, Ryan and Leo switched off on the keyboard and helped one another formulate ideas and transition from paper to code
*Ryan did DotTest; BoardTest was collaborative with Leo taking lead on creating the preset board
*Ryan worked on the readme.txt and commenting the functions
*Leo rewrote code on multiple occassions for readability
*Ryan devised better ways to make code compact on multiple occassions
