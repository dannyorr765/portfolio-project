# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## 2026.02.06

### Added

- Designed a Neuron component
- Designed a Synapse component
- Designed a Brain component

## 2026.03.02

### Added

- Designed a proof of concept for Synapse component

### Updated

- Added clamp helper method to simulate int8 behavior
- Added WEIGHT_SHIFT constant for fixed-point integer math in applyWeight

## 2026.03.10

### Added

- Created and implemented Synapse.java and SynapseKernel.java

### Updated

- Changed Synapse.java to Synapse1.java
- Updated Synapse1.java to extend from Standard

## 2026.03.30

### Added

- Created NeuronKernel.java with kernel method contracts
- Created Neuron.java with secondary method contracts
- Created Neuron1.java with full kernel and secondary implementations
- Created IONeuron.java with role and index contracts for IO neurons
- Created IONeuron1.java as concrete implementation of IONeuron

### Updated

- Added eligibility methods to SynapseKernel.java and Synapse.java

## 2026.04.01

### Added

- Created NeuronSecondary.java with secondary method implementations and common methods
- Created SynapseSecondary.java with secondary method implementations and common methods

### Updated

- Updated Neuron1.java to extend NeuronSecondary instead of implementing Neuron directly
- Updated Synapse1.java to extend SynapseSecondary instead of implementing Synapse directly

## 2026.04.26

### Updated

- Updated Neuron1.java to include the Standard methods
- Updated Synapse1.java to include the Standard methods
- Added createNewRep() helper to both Neuron1 and Synapse1