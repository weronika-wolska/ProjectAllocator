package entities;

import exceptions.InvalidArgumentException;
import exceptions.NoRandomChangeWasMadeException;
import interfaces.AssignmentAlgorithm;
import java.util.Random;

import static java.lang.Math.exp;

public class SimulatedAnnealing implements AssignmentAlgorithm{
    private CandidateSolution currentSolution;
    private Double startTemp;
    private Double endTemp;
    private Integer coolingCount;

    public SimulatedAnnealing(CandidateSolution originalSolution) {
        this(originalSolution, (double) 1, (double) 0, 5000);
    }

    public SimulatedAnnealing(CandidateSolution originalSolution, Double startTemp, Double endTemp, Integer coolingCount) {
        currentSolution = originalSolution;
        setStartTemp(startTemp);
        setEndTemp(endTemp);
        setCoolingCount(coolingCount);
    }

    public void anneal() throws InvalidArgumentException {
        //System.out.println("STARTING ANNEAL HERE");
        Double tempChange = calculateSystematicTempChange();
        Double currentTemp = startTemp;
        //System.out.println("ANNEAL HERE with temp:" + currentTemp + " and energy:" + currentSolution.getEnergy());
        for(; currentTemp > endTemp; currentTemp -= tempChange) {
            //System.out.println("ANOTHER ITERATION OF ANNEAL:" + currentTemp + " " + endTemp);
            double currentEnergy = currentSolution.getEnergy();
            currentSolution.makeRandomChange();
            double potentialNewEnergy = currentSolution.getEnergy();
            double changeInEnergy = potentialNewEnergy - currentEnergy;
            //System.out.println("In the iteration still with:" + currentEnergy + " " + potentialNewEnergy + " " + changeInEnergy);
            if(!shouldAcceptChangeAtTemp(changeInEnergy, currentTemp)) {
                try {
                    currentSolution.undoRandomChange();
                }
                catch (NoRandomChangeWasMadeException nrce) {
                    //System.out.println("In SimulatedAnnealing, CandidateSolution.undoRandomChange is called and an exception is thrown:");
                    nrce.printStackTrace();
                }
            }
        }
        //System.out.println("ENDING ANNEAL HERE with temp:" + currentTemp + " and energy:" + currentSolution.getEnergy());
    }

    public Double calculateSystematicTempChange() {
        return (startTemp - endTemp) / coolingCount;
    }

    public boolean shouldAcceptChangeAtTemp(double change, double currentTemp) {
        return change < 0 || doesBoltzmanAcceptThisChange(change, currentTemp);
    }

    public boolean doesBoltzmanAcceptThisChange(double change, double currentTemp) {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        double probabilityOfAcceptance = getBoltzmanProbabilityOfAccepting(change, currentTemp);
        //System.out.println("In sATC with:" + change + " and " + currentTemp + " and " + randomNumber + " and " + probabilityOfAcceptance + " and " + (randomNumber <= probabilityOfAcceptance));
        return randomNumber <= probabilityOfAcceptance;
    }

    public double getBoltzmanProbabilityOfAccepting(double change, double currentTemp) {
        if(currentTemp == 0) {
            if(change == 0) {
                //System.out.println("In gBPOA with:" + change + " and " + currentTemp + " and " + 1 / exp(1));
                return 1 / exp(1);
            }
            else {
                //System.out.println("In gBPOA with:" + change + " and " + currentTemp + " and " + 0);
                return 0;
            }
        }
        else {
            //System.out.println("In gBPOA with:" + change + " and " + currentTemp + " and " + 1 / exp(change / currentTemp));
            return 1 / exp(change / currentTemp);
        }
    }

    public CandidateSolution getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(CandidateSolution currentSolution) {
        this.currentSolution = currentSolution;
    }

    public Double getStartTemp() {
        return startTemp;
    }

    public void setStartTemp(Double startTemp) {
        if(startTemp <= 0) {
            if(this.startTemp == null) {
                startTemp = 100d;
            }
            else {
                startTemp = this.startTemp;
            }
        }
        if(endTemp != null) {
            if(startTemp < endTemp) {
                if(this.startTemp == null) {
                    startTemp = endTemp * 2;
                }
                else {
                    startTemp = this.startTemp;
                }
            }
        }
        /*if(endTemp != null && this.startTemp != null) {
            System.out.println("Setting startTemp:" + startTemp + " " + this.startTemp + " " + endTemp);
        }*/
        this.startTemp = startTemp;
    }

    public Double getEndTemp() {
        return endTemp;
    }

    public void setEndTemp(Double endTemp) {
        if(endTemp > startTemp) {
            if(this.endTemp == null) {
                endTemp = (double) 0;
            }
            else {
                endTemp = this.endTemp;
            }
        }
        this.endTemp = endTemp;
    }

    public Integer getCoolingCount() {
        return coolingCount;
    }

    public void setCoolingCount(Integer coolingCount) {
        if(coolingCount <= 0) {
            if(this.coolingCount == null) {
                coolingCount = 1;
            }
            else {
                coolingCount = this.coolingCount;
            }
        }
        this.coolingCount = coolingCount;
    }

    @Override
    public void createAssignment() {
        try {
            anneal();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CandidateSolution giveOutput() {
        return currentSolution;
    }
}
