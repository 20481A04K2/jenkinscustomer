
// shared-library/vars/deployApp.groovy
def call(String bucketName) {
    echo "🚀 Deploying app to GCP bucket: ${bucketName}"
    sh """
        echo 'Deleting old files from GCS...'
        gsutil -m rm -r gs://${bucketName}/** || true
        echo 'Uploading new build files...'
        gsutil -m cp -r build/* gs://${bucketName}/
    """
}
