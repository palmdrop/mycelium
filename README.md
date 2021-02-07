<!-- PROJECT LOGO -->
<br />
<p align="center">

  <h1 align="center">Mycelium</h1>

  <p align="center">
    A personal project which explores an algorithm not at all like mycelium growth, but with striking visual similarities. 

  </p>
</p>

![Pillars](/pictures/example-pillars2.png)

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></i>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact and social media</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

This is an old project of mine that I've touched up slightly to make it presentable for GitHub. Similar to my [sandbox project](https://github.com/palmdrop/sandbox), the purpose of this project is to allow me to explore code-driven art. In the case of this project, a particle simulation coupled with a specific type of "flow field" is used to produce an effect similar to that of mycelium growing. Note: this is by no means a mycelium simulation, the name merely comes from the visual similarities between the outputs of this project and that of growing mycelium.

In this stage, there is no user interface -- instead, the settings are changed by editing the source code itself. A short usage guide can be found under <a href="#usage">usage</a>. Perhaps in the future I'll add an interface for more easy use.

Some of the images produced by this project have been published on my [instagram](https://www.instagram.com/palmdrop/). Occasionally, I also make [blog posts](https://palmdrop.github.io/) describing parts of my process.

Here are a few sample images:

![Bridge](/pictures/example-circle1.png)
![Mess](/pictures/example-growth1.png)
![Text](/pictures/example-swirl1.png)

More can be found under `pictures`.

The core of the project is a particle physics simulation. Thousands of particles are spawned and each daws a trail as it moves. The trails typically have low opacity, and the color may change during the lifetime of the particle. After a set amount of time, the particle dies and new ones are spawned. 

The particles move according to a physics simulation. Often, gravity is applied in combination with a type of flowfield produced by a set of packed circle repellants. I.e, a set of circles are placed and the closer a particle is to the center of any circle, the greater is the force pushing said particle away from that center. The circles are placed using Poisson disk sampling. Here's a debug view of a typical, underlying heightmap structure: 

![Debug](/pictures/example-debug.png)

The density and size of the circles are varied using an underlying noise function. They are also allowed to overlap to avoid spots where no force is acting on the particles.

### Built With

The project is built with the aid of [Processing.](https://processing.org/) However, the code is not formatted as a processing sketch. Instead, the processing core is imported as a library to ensure that I could use a regular IDE (like IntelliJ IDEA) for organizing the code. More about installation/prerequisites under <a href="#getting-started">getting started</a>.

(A parenthesis about using Processing with any IDE: [this](https://happycoding.io/tutorials/java/processing-in-java) is a great blog post explaining how it can be done.)

<!-- GETTING STARTED -->
## Getting Started

I suggest setting up the project in a popular IDE for easy use since there's no installation script (and as mentioned, no user interface). I'll go through how to run the project using IntelliJ IDEA, but the process shouldn't be very different from other IDE's.

### Prerequisites

* [JDK 11](https://openjdk.java.net/projects/jdk/11/) (or newer)
* [Processing 3.5.4](https://processing.org/download/) (or newer)

### Installation

1. Install JDK 11 or newer.
2. Install processing 3.5.4 or newer.
3. Clone this repository
   ```sh
   git clone https://github.com/palmdrop/Mycelium.git
   ```
3. Setup a java project (below follows some steps for setting up an IntelliJ IDEA project, but any IDE might be used)
    * Open the `Mycelium` repository you've just cloned as a project.
    * Under `Project structure -> Project` pick Java 11 as the Project SDK (also make sure the language level is at least 11).
    * Under `Project structure -> SDKs` choose `11` (for Java 11) and then click `classpath`. Press `+` and navigate to where you installed Processing. Pick `processing-3.5.X/core/library/core.jar`. 
4. Run `Main`.

<!-- USAGE EXAMPLES -->
## Usage

Run the `Main` java file to run the project. The main class contains a `FlowSettings` field. The `FlowSettings` class contains all the classes and values required for the application to run. I recommend either creating your own class that extends this class, or edit the contents of `TestFlowSettings` where I've done most of my experimenting. You'll find a lot of possible values of different fields commented out. Try uncommenting some of them and see what happens.

When running the application, generation is slow. Most of the examples took a few minutes to render. The current state of the render will be displayed every second (roughly). While rendering, at any time you might press various keys to reset, pause/unpause, toggle debug mode, etc. 

Here are the existing key bindings:

* `r`     - Reset. Will start generating a new image using the same settings.
* `space` - Toggle pause.
* `d`     - Toggle debug view. This will simply display the underlying circles on the screen.
* `m`     - Will display a lower quality version of the render to speed up generation.
* `h`     - Will hide the render from the user to speed up generation.
* `s`     - Will save the image. The image will be saved under `pictures` if nothing else is specified. 

<!-- CONTRIBUTING -->
## Contributing

I do not expect any contributions to this project. However, if you do find an especially interesting configuration, or happen to use some of this code for a project of your own, feel free to share.

<!-- CONTACT -->
## Contact and social media
:mailbox_with_mail: [Email](mailto:anton@exlex.se) (the most reliable way of reaching me)

:camera: [Instagram](https://www.instagram.com/palmdrop/) (where I showcase a lot of my work)

:computer: [Blog](https://palmdrop.github.io/) (where I occasionally write posts about my process)


