# GameObject
Contains list of Components (figure out how to make it compile-time dependent) and get references to other components in the object

# Component
Has an apply() method
## Physical
Contains a BitBody
## Stateful
Contains a list of states, emits Commands? when state changes
## Animatable
Requires Drawable
Requires Stateful
Contains a map of states to animations
Animations have a sequence of Texture2D, time info, repeat, breakable, emits Commands? when certain frames are hit
## Drawable
Contains a Texture2D and some way to determine its size

# Trigger extends Component
Has an apply method
Contains a list of other Triggers

# Command

# InputHandler extends Component
