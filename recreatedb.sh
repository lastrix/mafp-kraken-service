#!/bin/bash

dropdb -U postgres test_mafp_kraken_service && createdb -U postgres test_mafp_kraken_service -T template0 -l en_US.utf-8 -O test
