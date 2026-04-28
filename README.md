# Neuron/Synapse Component

These are biologically-inspried neural network primitives built using the OSU software dicipline. This project provides a formal hierarchy for Neuron and Synapse objects, designed as the foundation for a larger SNN (spiking neural network) simulation.

## Overview

This project was created as part of the OSU CSE 2231 portfolio project. The goal was to formalize a neural network implementation originally programmed in C++ to follow OSU software guidelines, component hierarchy, complete with kernel implementations, secondary abstract classes, and complete implementations.

The two primary components are:

- **Neuron** - models a biological neuron with potential, threshold, decay rate, acitivity tracking, and a set of outgoing synapses. A neuron fires when it's potential exceeds it's threshold, propagating signals through it's set of synapses.
- **Synapse** - models a synaptic connection between two neurons. Carries a weighted signal from it's source, stimulating it's target neuron. Weights are stored as integers in the range 0 through 255 to simulate int8 behavior from the C++ version.

## Project Structure

src/
  Neuron.java             # Enhanced interface
  NeuronKernel.java       # Kernel interface
  NeuronSecondary.java    # Abstract secondary class
  Neuron1.java            # Kernel implementation
  IONeuron.java           # IO neuron interface
  IONeuron1.java          # IO neuron implementation
  Synapse.java            # Enhanced interface
  SynapseKernel.java      # Kernel interface
  SynapseSecondary.java   # Abstract secondary class
  Synapse1.java           # Kernel implementation
  NeuronDemo.java         # Use case 1: signal propagation demo
  NeuronCluster.java      # Use case 2: neuron cluster (Brain component placeholder)
test/
  Neuron1Test.java        # Kernel and Standard method tests
  NeuronTest.java         # Secondary method tests
  Synapse1Test.java       # Kernel and Standard method tests
  SynapseTest.java        # Secondary method tests
lib/
  components.jar          # OSU components library (needed for Set of synapses)
doc/                      # Project assignment documents
  ...

## Design notes

This project is intentially simplistic in nature. These components were built to be easily scalable to network sizes exceeding a billion neurons, with tens of billions of synapses or more. Every byte counts, which is what drove the use of integers over floating-point numbers for this project. 

A third component, Brain, was never translated over from the C++ implementation, mainly due to time constraits and wanting to keep the Java version simple. Brain would contain a set of Neurons, and handle everything from firing neurons, to applying synaptic weights, to applying rewards. The current components are the foundation to what Brain would build on.

## Dependencies

- [OSU Components JAR](https://cse22x1.engineering.osu.edu/common/components.jar)
- JUnit 4.13.2

## Author

Danny Orr
