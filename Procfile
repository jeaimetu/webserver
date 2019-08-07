pipinstall: pip install -r requirements.txt
pipupgrade: pip install --upgrade pip
web: gunicorn tms_was.wsgi
worker: python -u worker.py