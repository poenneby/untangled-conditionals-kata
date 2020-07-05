import dependencies.Logger;
import dependencies.Project;

class TestsStep extends PipelineStep {

    TestsStep(PipelineStep nextStep, Logger log) {
        super(nextStep, log);
    }

    PipelineStatus run(Project project, PipelineStatus pipelineStatus) {
        if (project.hasTests()) {
            if ("success".equals(project.runTests())) {
                log.info("Tests passed");
                return nextStep.run(project, pipelineStatus.withTestsPassed());
            } else {
                log.error("Tests failed");
                return pipelineStatus.withTestsFailed();
            }
        } else {
            log.info("No tests");
            return nextStep.run(project, pipelineStatus.withTestsPassed());
        }
    }
}