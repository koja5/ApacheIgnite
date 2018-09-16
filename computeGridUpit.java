try (Ignite ignite = Ignition.start(defaultIgniteCfg("cache-reading-compute-engine"))) {
    long gradId = 1;
    ignite.compute().affinityCall("SQL_PUBLIC_GRAD", gradId, new IgniteCallable<List>() {
        private static final long serialVersionUID = -131151815825938052L;
        @IgniteInstanceResource
        private Ignite currentIgniteInstance;
        @Override
        public List call() throws Exception {
            List imenaOsoba = new ArrayList();
            //pristupamo kesu sa osobama. 
            //koristeci withKeepBinary znaci da ce objekti vraceni iz kesa biti u BinaryObject formatu, ukoliko je to moguce
            IgniteCache osobaCache = currentIgniteInstance.cache("SQL_PUBLIC_OSOBA").withKeepBinary();
            IgniteBiPredicate filter = (BinaryObject key, BinaryObject value) -> {
                return key.hasField("GRAD_ID") && key.field("GRAD_ID") == gradId;
            };
            ScanQuery query = new ScanQuery(filter);
            try (QueryCursor<Entry> cursor = osobaCache.query(query)) {
                Iterator<Entry> itr = cursor.iterator();
                while (itr.hasNext()) {
                    Entry cache = itr.next();
                    imenaOsoba.add(cache.getValue().field("IME"));
                }
            }
            return imenaOsoba;
         }
     }).forEach(System.out::println);;
}