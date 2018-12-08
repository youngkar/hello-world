README 07 - Drew Bodmer, Karli Young, Deepak Ramesh 






Refactors to model:
- The discrete motion classes (move, resize, recolor) were removed in favor of a single Transformation class containing the old shape state and updated shape state.
- Transformations interface/implementation class has been changed to Keyframe interface and KeyframeImpl class. Transformations were essentially already keyframes, except they held excessive data (start AND begin time, old shape AND new shape). We changed them to keyframes so as to not hold onto that extra data.
- Changed our HashMap<String, AShape> in our animator to a LinkedHashMap<String, AShape> so we could ensure the order of the shapes in the map would stay the same as the order in which they were added (which is necessary for drawing the shapes in the correct order in the animation). We also changed the map in the AnimatorBuilder to a LinkedHashMap, for the same reason.




Refactors to the view:
- Added Appendable field to TextView object and constructor so that calling its print method now appends to the given Appendable; now it can write to System.out or to an external file.
- Deleted TextView, SVGView and VisualView interfaces; all implementation classes now extend the same View interface and have a “go” method that runs its main functionality.
- SVG and Visual view were revised to work with new Keyframe implementation.


- Added CompositeView interface, which extends the View interface, and holds its own methods for its own necessary functionality
        - Methods in the CompositeView interface:
• void addActionListener 
• void addItemListener 
• void addListSelectionListener - Adds the specified listeners to receive action events from the specified UI items.
• void pauseAnimation - pauses the animation
• void resumeAnimation - resumes the animation
• void restartAnimation - restarts the animation
• void enableLoop - enables looping for this animation
• void disableLoop - disables looping for this animation
• void increaseSpeed - increases the speed of this animation
• void decreaseSpeed - decreases the speed of this animation 
• void addShapeDialog - creates a dialog box for a user to add input for adding a shape
• int getAddShapeTimeInput - returns the time at which a user specifies they want to add a shape
• AShape getShapeToEdit - returns the shape the user has selected in the list of shapes in the UI
• AShape getNewShapeInput - returns a new shape object representing the shape that the user wants to add to the animation
• Keyframe getKFToAdd - returns a new Keyframe object representing the Keyframe the user wants to add to the animation
• Keyframe getEditedKF - returns a new Keyframe object representing the edited version of the keyframe they have selected
• Keyframe getKFToEdit - returns the Keyframe that the user has selected in the list of Keyframes in the UI
• void updateLists - updates the UI to show the updated list of shapes
• void findKeyframes(AShape) - updates the UI to show all the keyframes of the selected shape
• void addKeyframeDialog - creates a dialog box for a user to add input for adding a Keyframe to the selected shape
• void editKeyframeDialog - creates a dialog box for a user to add input for editing a selected Keyframe


- Added CompositeViewImpl, which implements the CompositeView interface contains a ROAnimator, canvas width and canvas height in the constructor. Our Composite view is designed to pull data from the ROAnimator it’s given. 




Controller: 
- Added Controller interface containing a single runAnimation method that makes the program start running. 
- Added Controller class which contains a ROAnimator and CompositeView.
        - Controller class contains three private methods (configureButtonListener, configureItemListener and configureListSelectListener) which creates and sets button listeners, check box listeners, and list select listeners for the view. In effect it creates snippets of code as Runnable objects, one for each time a button is pressed, check box is selected/deselected, or item in the JList is selected, only for those that the program needs.
- Added ButtonListener class which implements ActionListener interface. Its purpose is to run some action when particular buttons are pressed. Besides the override method from the ActionListener interface, this class also has a function that sets up a map of action commands to runnables, so that when a button is pressed, the correction action command causes the correct reaction (functionality of the associated runnable). 
- Added CheckBoxListener class which implements ItemListener interface. Its purpose is to run some action when a checkbox is checked, and run a different action when the checkbox is unchecked. 
- Added ListSelectListener class which implements ListSelectionListener interface. Its purpose is to run some action when an item in a list is selected. 




-All hashmaps were updated to linkedhashmaps to preserve the order of the shapes in the map and make sure they are printed out correctly.