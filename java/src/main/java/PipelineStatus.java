class PipelineStatus {

    final boolean testsPassed;
    final boolean deploySuccessful;

    private PipelineStatus(boolean testsPassed, boolean deploySuccessful) {
        this.testsPassed = testsPassed;
        this.deploySuccessful = deploySuccessful;
    }

    private PipelineStatus() {
        testsPassed = false;
        deploySuccessful = false;
    }

    static PipelineStatus empty() {
        return new PipelineStatus();
    }

    PipelineStatus withTestsPassed() {
        return new PipelineStatus(true, this.deploySuccessful);
    }

    PipelineStatus withTestsFailed() {
        return new PipelineStatus(false, this.deploySuccessful);
    }

    PipelineStatus withDeploymentSuccessful() {
        return new PipelineStatus(this.testsPassed, true);
    }

    PipelineStatus withDeploymentFailed() {
        return new PipelineStatus(this.testsPassed, false);
    }
}
