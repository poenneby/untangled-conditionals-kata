import dependencies.Logger;
import dependencies.Project;

abstract class PipelineStep {
    final PipelineStep nextStep;
    final Logger log;

    PipelineStep(PipelineStep nextStep, Logger log) {
        this.nextStep = nextStep;
        this.log = log;
    }

    abstract PipelineStatus run(Project project, PipelineStatus pipelineStatus);
}
