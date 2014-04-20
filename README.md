xbrl2rdf
========

This is a command line application that can transform XBRL documents to linked data. It follows the [xCurator](https://github.com/ekzhu/xcurator) framework for XML schema extraction and RDF generation, with specialized schema discoverer for XBRL.

##To build (need Ant):

1. Check out the repo.
2. Run `ant init` to resolve dependencies
3. Currently the xCurator dependency cannot be resolved automatically. You need to build it yourself. See instruction [here](https://github.com/ekzhu/xcurator). Once built, copy the `xcurator.jar` to the `lib` directory.
4. Run `ant clean dist` to build afresh.
5. The runnable JAR file `xbrl2rdf.jar` is in `dist` directory

##To run:

In the directory of `xbrl2rdf.jar`, run `java -jar xbrl2rdf.jar` to see help messages.