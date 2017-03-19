# Set up for gcloud
gcloud config set account your-login
gcloug auth login

# create project via UI
# Visit https://console.cloud.google.com/kubernetes/list so that the container engine stuff is set up
# remove default firewall rules for ssh and rdp

# Set up for the project
gcloud config set project scoutmgr-159921

# Create cluster
gcloud container clusters create scoutmgr --num-nodes=1 --node-labels=tags=gke-scoutmgr-node
# Created
#  1 vm, with disk
#  external ip,
#  3 firewall rules to allow access to the cluster from within the cluster and from google's servers
#  route within the custer
gcloud container clusters get-credentials scoutmgr

# Start k8s console so we can see what is going on
kubectl config set-context gke_scoutmgr-159921_asia-northeast1-a_scoutmgr
kubectl proxy &
open http://localhost:8001/ui

# Set up postgres - https://blog.oestrich.org/2015/08/running-postgres-inside-kubernetes/
# First create a disk and format it
gcloud compute disks create pg-data-disk --size 2GB
gcloud compute instances attach-disk  gke-scoutmgr-default-pool-76146374-lsm5	 --disk pg-data-disk
# Add an ssh rule to allow connection from your local IP
gcloud compute --project "scoutmgr-159921" ssh --zone "asia-northeast1-a" "gke-scoutmgr-default-pool-76146374-lsm5"
ls /dev/disk/by-id
sudo mkfs.ext4 -F -E lazy_itable_init=0,lazy_journal_init=0,discard /dev/disk/by-id/google-persistent-disk-1
# Don't know that we really need to do the next 5 lines
sudo mkdir -p /mnt/disks/pg-data
sudo mount -o discard,defaults /dev/disk/by-id/google-persistent-disk-1 /mnt/disks/pg-data
sudo chmod a+w /mnt/disks/pg-data
sudo umount /mnt/disks/pg-data
exit
gcloud compute instances detach-disk   gke-scoutmgr-default-pool-76146374-lsm5	 --disk pg-data-disk

# Now link it to the cluster
kubectl create -f postgres-persistence.yml
kubectl create -f postgres-claim.yml

# Launch the PG deployment
kubectl create -f postgres-deployment.yml
kubectl get pods
kubectl logs postgres-magicid
kubectl rollout history deployment/postgres

# Open tunnel to postgres pod so we can create db
kubectl port-forward postgres-634962460-d2cbt 9999:5432
buildr dbt:timers:create DBT_ENV=production
buildr dbt:create DBT_ENV=production

# Create a postgres service so the rest of the apps can find it
kubectl create -f postgres-service.yml

# Create scoutmgr deployment

# Create scoutmgr service open to public




# troubleshooting
# Connect to database via ssh tunnel