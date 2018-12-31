using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace My.Library.RNMyLibrary
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNMyLibraryModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNMyLibraryModule"/>.
        /// </summary>
        internal RNMyLibraryModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNMyLibrary";
            }
        }
    }
}
