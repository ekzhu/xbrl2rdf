xbrl2rdf
========

This is a command line tool for converting XBRL documents to Resource Description Framework (RDF) format. It uses [xCurator](https://github.com/ekzhu/xcurator). It can be used together with [secxbrl](https://github.com/ekzhu/secxbrl), which downloads the URLs of XBRL documents from SEC.

##Build

Building this tool requires [Ant](http://ant.apache.org). Run `ant -version` to see if it is installed.

1. Check out the repo.
2. Run `ant init` to resolve dependencies
3. Currently the xCurator dependency cannot be resolved automatically. You need to build it yourself. See instruction [here](https://github.com/ekzhu/xcurator). Once built, copy the `xcurator.jar` to the `lib` directory.
4. Run `ant clean dist` to build afresh.
5. The runnable JAR file `xbrl2rdf.jar` is in `dist` directory

##Quick start

Follow the instructions for building. Once done, in the directory of `xbrl2rdf.jar`, run the following commands

	mkdir tdb
	java -jar xbrl2rdf.jar -d https://www.sec.gov/Archives/edgar/data/1326801/000132680114000007/fb-20131231.xml -h http://corpbase.org -m fb-20131231-mapping.xml -t tdb

This will download Facebook Inc.'s 2013 annual filing, and convert the document into RDF data. The RDF data is stored in a [TDB](http://jena.apache.org/documentation/tdb/), which is in the `tdb` directory. The entities and relations in the data are serialized into the mapping file `fb-20131231-mapping.xml`. The domain name of the RDF data is set to `http://corpbase.org`.

Local XBRL documents can also be used, just use local file path for `-d` argument.

##Serve data over HTTP

[Fuseki](http://jena.apache.org/documentation/serving_data) can be used for serving the RDF data over HTTP through [SPARQL](http://www.w3.org/TR/sparql11-query/) endpoint. It also provides a nice web interface for running SPARQL queries.

For quick start, [download](http://jena.apache.org/download) Fuseki, then run the following command:

	bash fuseki-server --loc /path/to/tdb/directory /corpbase

The web query interface can now be accessed at `http://localhost:3030/control-panel.tpl`, select `/corpbase` dataset.

##Query the data

The data can be queried directly through the SPARQL endpoint served by Fuseki server, either using [ARQ](http://jena.apache.org/documentation/query/) or the web query interface provided by Fuseki server.

You can use the following SPARQL query to get the net income information in the filings.

	prefix class: <http://corpbase.org/resource/class/>
	prefix property: <http://corpbase.org/resource/property/>
	prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>

	select distinct ?name ?netincome ?start ?end
	where
	{
	?a rdf:type class:xbrl.
	?a property:EntityRegistrantName ?n.
	?n property:value ?name.
	?a property:NetIncomeLoss ?z.
	?z property:value ?netincome.
	?z property:context ?context.
	?context property:entity ?contextentity.
	filter not exists {?contextentity property:segment ?x}.
	?context property:period ?period.
	?period property:startDate ?start.
	?period property:endDate ?end.
	}
	order by ?name

[Apache Jena](http://jena.apache.org) provides many other tools and libraries to work with RDF data, vist their website for more information.
