[![Build Status](https://travis-ci.org/winner-is-kungen/TDA367.svg?branch=master)](https://travis-ci.org/winner-is-kungen/TDA367)

# TDA367
A *nicer* alternative to DigiFlisp.

## Building
We use maven for dependencies and building.  
To run the application during testing, use `mvn javafx:run`.  
To build an exportable stand-alone program, use `mvn javafx:jlink`.

## How-to
When you run the application you have to load a workspace before you start working. To load a workspace press "file" in menu bar then "Open Workspace". You can now select the directory you would like to start working in.

When a directory is loaded you can create blueprints, files, using the menu bar option "New File" to create blueprints and start working.

When a blueprint is created you can start creating components inside of the blueprint, simply click the component you would like to create on the left side of the application.

To connect components left click on the output/input of the component you would like to connect and then on the output/input you would like to connect it to.

Simulations are always running, but to be able to interact with the simulation create "Input" components that let you modify their value when "alt" + left clicking the input component.

You can then use an "output" component to see the output value of your build.

Black = Low
Green = High
Yellow = Active

Active means that you clicked this connection point and are about to connect it to something.

If you want to remove connections simply click on them.

When you want to save your file, simply press the menu bar option "Save File" to save the file currently in focus.

There is also a scroll feature and a feature to "move" around the workspace visually. Scroll using mousewheel and move around the workspace when holding down middle mouse button. However there are some known issues with the connections not being displayed properly when zoomed out/in.

## Participants
* Obada Al Khayat
* Joakim Anderlind or Deltaphish under joakimanderlind@gmail.com
* Lukas Andersson
* Alexander Grönberg
* Mårten Åsberg
