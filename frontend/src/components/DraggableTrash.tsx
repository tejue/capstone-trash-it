import {TrashType} from "../types/TrashType.ts";
import styled from "styled-components";
import {CSS, Transform} from "@dnd-kit/utilities";
import {useDraggable} from "@dnd-kit/core";

type DraggableTrashProps = {
    trash: TrashType;
    index: number;
}

export default function DraggableTrash(props: Readonly<DraggableTrashProps>) {
    const {attributes, listeners, setNodeRef, transform} = useDraggable({id: props.trash.id});

    return (
        <StyledDraggableTrash
            id={props.trash.id}
            ref={setNodeRef}
            $index={props.index}
            transform={transform}
            {...attributes}
            {...listeners}>
            <StyledImage src={`${props.trash.image}`} alt={`${props.trash.name}`}/>
        </StyledDraggableTrash>
    )
}

type StyledDraggableTrashProps = {
    transform: Transform | null;
    id: string;
    $index?: number
}

const StyledDraggableTrash = styled.div.attrs<StyledDraggableTrashProps>(
    ({
         transform, $index
     }) => ({
        style: {
            transform: CSS.Transform.toString(transform),
            gridArea: `area${$index}`,
            touchAction: 'none'
        }
    })
)`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8px;
  height: auto;
  box-sizing: border-box;
`

const StyledImage = styled.img`
  max-height: 100%;
  max-width: 100%;
  rotate: 10deg;
  `
