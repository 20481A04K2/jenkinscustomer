def call(String bucketName) {
    echo "🚀 Deploying to GCP Bucket: ${bucketName}"

    sh """
        echo "Deploying artifacts to ${bucketName}..."
        mkdir -p deploy
        echo "Files deployed to ${bucketName}" > deploy/deploy.log
    """

    echo "✅ Deployment completed successfully to ${bucketName}."
}
