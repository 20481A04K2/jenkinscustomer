def call(String bucketName) {
    echo "🚀 Deploying build artifacts to GCS Bucket: ${bucketName}"

    // Make sure gsutil exists
    sh 'which gsutil || (echo "⚠️ gsutil not found — please install gcloud SDK on this Jenkins node" && exit 1)'

    // Upload build artifacts (assuming they exist in ./build)
    sh """
        echo "Uploading build artifacts to gs://${bucketName}/ ..."
        gsutil -m cp -r build/* gs://${bucketName}/
    """

    echo "✅ Deployment completed successfully to ${bucketName}."
}
